package cn.bugstack.knowledge.config;

import io.micrometer.observation.ObservationRegistry;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.DefaultChatClientBuilder;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.observation.ChatClientObservationConvention;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class OpenAIConfig {

    @Bean
    public TokenTextSplitter tokenTextSplitter() {
        return new TokenTextSplitter();
    }

    @Bean
    public OpenAiApi openAiApi(@Value("${spring.ai.openai.base-url}") String baseUrl, @Value("${spring.ai.openai.api-key}") String apikey) {
        return OpenAiApi.builder()
                .baseUrl(baseUrl)
                .apiKey(apikey)
                .build();
    }

    @Bean("openAiSimpleVectorStore")
    public SimpleVectorStore vectorStore(OpenAiApi openAiApi) {
        OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(openAiApi);
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    /**
     * -- 删除旧的表（如果存在）
     * DROP TABLE IF EXISTS public.vector_store_openai;
     *
     * -- 创建新的表，使用UUID作为主键
     * CREATE TABLE public.vector_store_openai (
     *     id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
     *     content TEXT NOT NULL,
     *     metadata JSONB,
     *     embedding VECTOR(1536)
     * );
     *
     * SELECT * FROM vector_store_openai
     */
    @Bean("openAiPgVectorStore")
    public PgVectorStore pgVectorStore(OpenAiApi openAiApi, JdbcTemplate jdbcTemplate) {
        OpenAiEmbeddingOptions openAiEmbeddingOptions =
                OpenAiEmbeddingOptions.builder()
                        .model("text-embedding-v4").dimensions(1536).build();
        OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(openAiApi
                , MetadataMode.EMBED, openAiEmbeddingOptions);
        return PgVectorStore.builder(jdbcTemplate, embeddingModel)
                .vectorTableName("vector_store_openai")
                .build();
    }

    @Bean
    public ChatClient.Builder chatClientBuilder(OpenAiChatModel openAiChatModel) {
        return new DefaultChatClientBuilder(openAiChatModel, ObservationRegistry.NOOP, (ChatClientObservationConvention) null);
    }

    //这个配置使得应用能够与AI模型进行交互，并利用工具来扩展AI的能力，例如访问文件系统、查询数据库等。
    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel, ToolCallbackProvider tools) {
        DefaultChatClientBuilder defaultChatClientBuilder = new DefaultChatClientBuilder(openAiChatModel, ObservationRegistry.NOOP, (ChatClientObservationConvention) null);
        return defaultChatClientBuilder
                .defaultTools(tools)
                .defaultOptions(OpenAiChatOptions.builder()
                        .model("qwen-plus")
                        .build())
                .defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory())) // 添加聊天记忆顾问，以便在对话中使用记忆功能
                .build();
    }
    // 创建一个InMemoryChatMemory对象，用于存储聊天记录，基于内存的聊天记忆实现
    // 这个Bean将被ChatClient使用，以便在每次对话中存储和检索聊天记录
    @Bean
    public ChatMemory chatMemory() {
        return new InMemoryChatMemory();
    }
}
