package com.example.fairsharebackend.Text;

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
    public OCRResult saveOCRResult(@RequestBody String resultText) {
        return ocrResultService.saveOCRResult(resultText);
    }

    @GetMapping("/getText/{id}")
    public Optional<OCRResult> getOCRResult(@PathVariable String id) {
        return ocrResultService.getOCRResult(id);
    }

    @GetMapping("/parseText/{id}")
    public String parseOCRResultWithChatGPT(@PathVariable String id) throws Exception {
        Optional<OCRResult> ocrResult = ocrResultService.getOCRResult(id);

        if (ocrResult.isPresent()) {
            String resultText = ocrResult.get().getResultText();
            String response = GPTService.getChatGPTResponse(resultText);

            gptService.saveChatGPTResponse(response);
            return response;

        } else {
            return "Text not found for id: " + id;
        }
    }
}
