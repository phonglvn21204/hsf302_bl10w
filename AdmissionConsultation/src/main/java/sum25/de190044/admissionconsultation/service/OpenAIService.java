package sum25.de190044.admissionconsultation.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    public String askChatGPT(String message) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String body = """
                {
                  "model": "gpt-3.5-turbo",
                  "messages": [
                    {"role": "system", "content": "Bạn là trợ lý tư vấn tuyển sinh."},
                    {"role": "user", "content": "%s"}
                  ]
                }
                """.formatted(message);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.openai.com/v1/chat/completions"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println("🟢 OpenAI RESPONSE:");
            System.out.println(response.body());
            // Đọc câu trả lời từ JSON
            return extractReply(response.body());
        } catch (Exception e) {
            return "Xin lỗi, tôi không thể phản hồi lúc này.";
        }
    }

    private String extractReply(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Xin lỗi, tôi không thể xử lý phản hồi.";
        }
    }
}

