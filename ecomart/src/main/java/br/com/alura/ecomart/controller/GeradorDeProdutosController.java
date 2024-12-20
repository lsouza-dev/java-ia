package br.com.alura.ecomart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gerador")
public class GeradorDeProdutosController {


    private final ChatClient chatClient;

    public GeradorDeProdutosController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }


    @GetMapping
    public String gerarProdutos(){
        var pergunta = "Gere 5 produtos ecologicos em pt-BR";

        return this.chatClient.prompt()
                .user(pergunta)
                .call()
                .content();
    }

}
