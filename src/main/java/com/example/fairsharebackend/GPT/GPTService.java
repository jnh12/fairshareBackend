package com.example.fairsharebackend.GPT;

import com.example.fairsharebackend.Image.OCRImage;
import com.example.fairsharebackend.Image.OCRImageRepository;
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
import java.util.Optional;

@Service
public class GPTService {

    @Autowired
    private GPTRepository gptRepository;

    @Value("${openai.api.key}")
    private String openaiApiKey;
    private String promptToGPT = "You are a data parser. I will provide you with unstructured text from a receipt. Your task is to extract and format the relevant information into a structured JSON output. Follow this structure exactly and ensure data accuracy. If a field is not present, use 'null'. Use this format:\n" +
            "{\n" +
            "  \"store\": {\n" +
            "    \"name\": \"string\", // Name of the store\n" +
            "    \"address\": \"string\" // Address of the store\n" +
            "  },\n" +
            "  \"transaction\": {\n" +
            "    \"date\": \"string\", // Date in ISO 8601 format e.g., \"2024-10-08\"\n" +
            "    \"total\": \"number\", // Total amount\n" +
            "    \"currency\": \"string\", // ISO 4217 currency code, e.g., \"USD\"\n" +
            "    \"payment_method\": \"string\" // Payment method, e.g., \"Credit Card\", \"Cash\"\n" +
            "  },\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"name\": \"string\", // Name of the item\n" +
            "      \"quantity\": \"number\", // Quantity of the item\n" +
            "      \"price\": \"number\" // Price of the item\n" +
            "    }\n" +
            "  ]\n" +
            "}\n" +
            "Instructions:\n" +
            "- Extract the store name and address from the text.\n" +
            "- Extract the transaction date, total amount, currency, and payment method.\n" +
            "- For each item, extract the name, quantity, and price. Ensure the item details are accurate.\n" +
            "- Use 'null' where data is missing or not applicable.\n" +
            "The data might be unordered, so ensure you understand the context before extracting information.";


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

    public void saveChatGPTResponse(String response, String deviceUUID) {
        try {
            GPTResponse gptResponse = objectMapper.readValue(response, GPTResponse.class);
            gptResponse.setDeviceUUID(deviceUUID);

            long count = gptRepository.countByDeviceUUID(deviceUUID);
            gptResponse.setFsId(String.valueOf(count));

            gptRepository.save(gptResponse);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<GPTResponse> findLatestGPTResponseByDeviceUUID(String deviceUUID) {
        return gptRepository.findTopByDeviceUUIDOrderByFsId(deviceUUID);
    }

}
