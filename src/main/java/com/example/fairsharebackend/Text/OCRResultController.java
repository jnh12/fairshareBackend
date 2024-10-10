package com.example.fairsharebackend.Text;

import com.example.fairsharebackend.GPT.GPTResponseRequest;
import com.example.fairsharebackend.GPT.GPTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ocr")
public class OCRResultController {

    @Autowired
    private OCRResultService ocrResultService;

    @Autowired
    private GPTService GPTService;

    @Autowired
    private GPTService gptService;

    @PostMapping("/saveText")
    public OCRResult saveOCRResult(@RequestBody OCRResultRequest request) {
        return ocrResultService.saveOCRResult(request.getDeviceUUID(), request.getResultText());
    }

    @GetMapping("/getText/{id}")
    public Optional<OCRResult> getOCRResult(@PathVariable String id) {
        return ocrResultService.getOCRResult(id);
    }

    @PostMapping("/parseText")
    public String parseOCRText(@RequestBody GPTResponseRequest request) throws Exception {
        String resultText = request.getResultText();
        String deviceUUID = request.getDeviceUUID();

        // Call the ChatGPT service with the OCR text
        String response = GPTService.getChatGPTResponse(resultText);

        // Save the response along with the UUID
        gptService.saveChatGPTResponse(response, deviceUUID);

        return response;
    }

}
