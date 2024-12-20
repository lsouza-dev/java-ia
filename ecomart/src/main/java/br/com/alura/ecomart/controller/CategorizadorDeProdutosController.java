package br.com.alura.ecomart.controller;

import br.com.alura.ecomart.configurations.ChatClientConfiguration;
import br.com.alura.ecomart.service.TokenService;
import com.knuddels.jtokkit.api.ModelType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptionsBuilder;
import org.springframework.ai.model.ModelOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categorizar")
public class CategorizadorDeProdutosController {

    private ChatClient chatClient = null;

    @Autowired
    private TokenService tokenService;

    public CategorizadorDeProdutosController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping
    public String categorizar(String produto){
        var system = """
                    Você é um categorizador de produtos e deve responder apenas o nome da categoria do produto informado

                        Escolha uma categoria dentro da lista abaixo:
                        1. Higiene pessoal
                        2. Eletrônicos
                        3. Esportes
                        4. Outros

                        ###### exemplo de uso:

                        Pergunta: Bola de futebol
                        Resposta: Esportes
                """;

        var tokens = tokenService.contarTokens(system,produto);
        System.out.printf("\nQuantidade de Tokens: %s",tokens);

        return this.chatClient.prompt()
                .system(system)
                .user(produto)
                .call()
                .content();
    }

}
