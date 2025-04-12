/*
package cn.bugstack.knowledge.utils;

import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.core.BotPlugin;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import com.mikuac.shiro.dto.event.message.PrivateMessageEvent;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class BotMainService extends BotPlugin {

    @Resource
    private ChatClient.Builder chatClientBuilder;

    @Autowired
    private ToolCallbackProvider tools;
    @Override
    public int onPrivateMessage(Bot bot, PrivateMessageEvent event) {//私聊
        if (!event.getMessage().isEmpty()) {
            log.info(event.getMessage());
            var chatClient = chatClientBuilder
                    .defaultTools(tools)
                    .defaultOptions(OpenAiChatOptions.builder()
                            .model("gpt-4o")
                            .build())
                    .build();
            bot.sendPrivateMsg(event.getUserId(), chatClient.prompt(event.getMessage()).call().content(), false);
        }
        return MESSAGE_IGNORE;
    }
    @Override
    public int onGroupMessage(Bot bot, GroupMessageEvent event) {//群聊回复
        if (!event.getMessage().isEmpty()) {
            var chatClient = chatClientBuilder
                    .defaultTools(tools)
                    .defaultOptions(OpenAiChatOptions.builder()
                            .model("gpt-4o")
                            .build())
                    .build();
            System.out.println("\n>>> ASSISTANT: " + chatClient.prompt(event.getMessage()).call().content());
            bot.sendGroupMsg(event.getGroupId(), chatClient.prompt(event.getMessage()).call().content(), false);
        }
        return MESSAGE_IGNORE;
    }
}*/
