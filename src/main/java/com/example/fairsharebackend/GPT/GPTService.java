package com.example.fairsharebackend.GPT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class GPTService {

    @Autowired
    private GPTRepository gptRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;
    private String promptToGPT = "Parse this data into this json format: {\n" +
            "  \"store\": {\n" +
            "    \"name\": \"string\",\n" +
            "    \"address\": \"string\"\n" +
            "  },\n" +
            "  \"transaction\": {\n" +
            "    \"date\": \"string\",  // ISO 8601 format e.g., \"2024-10-08\"\n" +
            "    \"total\": \"number\",\n" +
            "    \"currency\": \"string\",  // ISO 4217 currency code, e.g., \"USD\"\n" +
            "    \"payment_method\": \"string\"  // e.g., \"Credit Card\", \"Cash\"\n" +
            "  },\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"name\": \"string\",\n" +
            "      \"quantity\": \"number\",\n" +
            "      \"price\": \"number\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

    private final String apiUrl = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();


    public String getChatGPTResponse(String prompt) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", new Object[] {
                Map.of("role", "system", "content", promptToGPT),
                Map.of("role", "user", "content", prompt)
        });

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        JsonNode jsonResponse = objectMapper.readTree(response.getBody());
        return jsonResponse.get("choices").get(0).get("message").get("content").asText();
    }

    public void saveChatGPTResponse(String response) {
        try {
            GPTResponse gptResponse = objectMapper.readValue(response, GPTResponse.class);
            gptRepository.save(gptResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
