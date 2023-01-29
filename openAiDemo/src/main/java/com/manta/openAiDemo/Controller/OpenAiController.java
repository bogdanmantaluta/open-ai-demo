package com.manta.openAiDemo.Controller;

import com.manta.openAiDemo.Model.ConversationModel;
import com.theokanning.openai.OpenAiService;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.CompletionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OpenAiController {

    @PostMapping(value = "/start")
    public void Start(@RequestBody ConversationModel message) {

        OpenAiService openAiServiceForTheFirstAI = new OpenAiService(""); //insert openaikey here
        OpenAiService openAiServiceForTheSecondAI = new OpenAiService(" "); //insert openaikey here
        String messageFromRequest = message.getMessage();
        System.out.println("INTREBAREA DE LA CARE S-A PLECAT: ");
        System.out.println(messageFromRequest);

        while(true) {

            CompletionRequest completionRequest = CompletionRequest.builder()
                    .maxTokens(150)
                    .frequencyPenalty((double) 0)
                    .temperature(0.9).topP(1.0)
                    .presencePenalty(0.6)
                    .prompt(messageFromRequest)
                    .build();

            CompletionResult resultFirstAI = openAiServiceForTheFirstAI.createCompletion("text-davinci-003", completionRequest);
            System.out.println("PRIMUL AI: ");

            String firstAIMessage = resultFirstAI.getChoices().get(0).getText();
            System.out.println(firstAIMessage);

            CompletionRequest completionRequestForSecondAI = CompletionRequest.builder()
                    .maxTokens(150)
                    .frequencyPenalty((double) 0)
                    .temperature(0.9).topP(1.0)
                    .presencePenalty(0.6)
                    .prompt(firstAIMessage+"Haide sa purtam o discutie pe tema asta.")
                    .build();

            CompletionResult resultSecondAI = openAiServiceForTheSecondAI.createCompletion("text-davinci-003", completionRequestForSecondAI);
            System.out.println("AL DOILEA AI: ");

            String secondAIMessage = resultSecondAI.getChoices().get(0).getText();
            messageFromRequest = secondAIMessage;
            System.out.println(secondAIMessage);

        }
    }
}
