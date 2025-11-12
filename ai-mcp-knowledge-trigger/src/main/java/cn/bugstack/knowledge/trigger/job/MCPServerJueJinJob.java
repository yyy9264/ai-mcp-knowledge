package cn.bugstack.knowledge.trigger.job;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MCPServerJueJinJob {
    @Resource
    private ChatClient chatClient;

    //@Scheduled(cron = "0 0/6 * * * ?")
    public void exec() {
        try {
            String userInput = """
                    我需要你帮我生成一篇文章，要求如下；
                    
                                        1. 场景为互联网大厂java求职者面试
                                        2. 提问的技术栈如下；
                    
                                            核心语言与平台: Java SE (8/11/17), Jakarta EE (Java EE), JVM
                                            构建工具: Maven, Gradle, Ant
                                            Web框架: Spring Boot, Spring MVC, Spring WebFlux, Jakarta EE, Micronaut, Quarkus, Play Framework, Struts (Legacy)
                                            数据库与ORM: Hibernate, MyBatis, JPA, Spring Data JDBC, HikariCP, C3P0, Flyway, Liquibase
                                            测试框架: JUnit 5, TestNG, Mockito, PowerMock, AssertJ, Selenium, Cucumber
                                            微服务与云原生: Spring Cloud, Netflix OSS (Eureka, Zuul), Consul, gRPC, Apache Thrift, Kubernetes Client, OpenFeign, Resilience4j
                                            安全框架: Spring Security, Apache Shiro, JWT, OAuth2, Keycloak, Bouncy Castle
                                            消息队列: Kafka, RabbitMQ, ActiveMQ, JMS, Apache Pulsar, Redis Pub/Sub
                                            缓存技术: Redis, Ehcache, Caffeine, Hazelcast, Memcached, Spring Cache
                                            日志框架: Log4j2, Logback, SLF4J, Tinylog
                                            监控与运维: Prometheus, Grafana, Micrometer, ELK Stack, New Relic, Jaeger, Zipkin
                                            模板引擎: Thymeleaf, FreeMarker, Velocity, JSP/JSTL
                                            REST与API工具: Swagger/OpenAPI, Spring HATEOAS, Jersey, RESTEasy, Retrofit
                                            序列化: Jackson, Gson, Protobuf, Avro
                                            CI/CD工具: Jenkins, GitLab CI, GitHub Actions, Docker, Kubernetes
                                            大数据处理: Hadoop, Spark, Flink, Cassandra, Elasticsearch
                                            版本控制: Git, SVN
                                            工具库: Apache Commons, Guava, Lombok, MapStruct, JSch, POI
                                            其他: JUnit Pioneer, Dubbo, R2DBC, WebSocket
                                        3. 提问的场景方案可包括但不限于；音视频场景,内容社区与UGC,AIGC,游戏与虚拟互动,电商场景,本地生活服务,共享经济,支付与金融服务,互联网医疗,健康管理,医疗供应链,企业协同与SaaS,产业互联网,大数据与AI服务,在线教育,求职招聘,智慧物流,供应链金融,智慧城市,公共服务数字化,物联网应用,Web3.0与区块链,安全与风控,广告与营销,能源与环保。               \s
                                        4. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答出来，回答好了面试官还会夸赞和引导。复杂问题含糊其辞，回答的不清晰。
                                        5. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                                        6. 提问后把问题的答案详细的，写到文章最后，讲述出业务场景和技术点，让小白可以学习下来。
                    
                                        根据以上内容，不要阐述其他信息，请直接提供；文章标题（需要含带技术点）、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                    
                                        将以上内容发布文章到稀土掘金
                    """;
            String content = chatClient.prompt(userInput).call().content();
            log.info("任务执行结果：{} {}", userInput, content);
        }catch (Exception e) {
            log.error("任务执行异常：{}",e);
        }
    }
    @Scheduled(cron = "0 0 19 * * ?")
    //@Scheduled(cron = "0 0/3 * * * ?")
    public void exec2() {
        try {
            String userInput = """
                    我需要你帮我生成一篇文章，要求如下；
                                
                1. 场景为互联网大厂java求职者面试
                2. 面试管提问 Java 核心知识、JUC、JVM、多线程、线程池、HashMap、ArrayList、Spring、SpringBoot、MyBatis、Dubbo、RabbitMQ、xxl-job、Redis、MySQL、Linux、Docker、设计模式、DDD等不限于此的各项技术问题。
                3. 按照故事场景，以严肃的面试官和搞笑的水货程序员谢飞机进行提问，谢飞机对简单问题可以回答，回答好了面试官还会夸赞。复杂问题胡乱回答，回答的不清晰。
                4. 每次进行3轮提问，每轮可以有3-5个问题。这些问题要有技术业务场景上的衔接性，循序渐进引导提问。最后是面试官让程序员回家等通知类似的话术。
                5. 提问后把问题的答案，写到文章最后，最后的答案要详细讲述出技术点，让小白可以学习下来。
                                
                根据以上内容，不要阐述其他信息，请直接提供；文章标题、文章内容、文章标签（多个用英文逗号隔开）、文章简述（100字）
                                
                将以上内容发布文章到JueJin。     
                                
                之后进行，微信公众号消息通知，平台：掘金、主题：为文章标题、描述：为文章简述、跳转地址：从发布文章到掘金获取文章的url
                """;
                String content = chatClient.prompt(userInput).call().content();
                log.info("任务执行结果：{} {}", userInput, content);
        }catch (Exception e) {
            log.error("任务执行异常：{}",e);
        }
    }
}
