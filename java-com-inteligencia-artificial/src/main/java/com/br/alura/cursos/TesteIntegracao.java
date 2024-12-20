package com.br.alura.cursos;


import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

public class TesteIntegracao {
    public static void main(String[] args) {

        var envVarName = "OPENAI_KEY";
        var key = System.getenv(envVarName);

        var user = "Gere 5 produtos";
        var system = "Você é um gerador de produtos de um e-commerce de eletrônicos que deve" +
                " responder apenas o nome dos produtos,por exemplo: Aspirador,Televisão,etc";

        var service = new OpenAiService(key);
        var request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .build();
        service.createChatCompletion(request).getChoices().forEach(System.out::println);

    }
}
