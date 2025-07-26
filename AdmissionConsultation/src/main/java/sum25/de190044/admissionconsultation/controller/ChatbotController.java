package sum25.de190044.admissionconsultation.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sum25.de190044.admissionconsultation.dto.ChatMessage;
import sum25.de190044.admissionconsultation.service.OpenAIService;

import java.util.Map;

@RestController
public class ChatbotController {

    private final OpenAIService openAIService;

    public ChatbotController(OpenAIService openAIService) {
        this.openAIService = openAIService;
    }

    @PostMapping("/chatbot")
    public Map<String, String> handleChat(@RequestBody ChatMessage request) {
        String reply = openAIService.askChatGPT(request.getMessage());
        return Map.of("reply", reply);
    }
}

