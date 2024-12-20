package br.com.alura.ecomart.configurations;

import com.knuddels.jtokkit.api.ModelType;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ChatClientConfiguration {

    @Bean
    @Primary
    @Qualifier("gpt-4o-mini")
    public ChatClient gpt4oMiniChatClient(ChatClient.Builder chatClientBuilder) {


        return chatClientBuilder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .defaultOptions(ChatOptionsBuilder
                        .builder()
                        .withModel("gpt-4o-mini")
                        .withTemperature(.8)
                        .build())
                .build();
    }

    @Bean
    @Qualifier("gpt-4o")
    public ChatClient gpt4oChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder
                .defaultOptions(ChatOptionsBuilder
                        .builder()
                        .withModel("gpt-4o")
                        .build())
                .build();
    }

}
