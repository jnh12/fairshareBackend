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

    @PostMapping("/parseText")
    public String parseOCRText(@RequestBody String resultText) throws Exception {
        // Call the ChatGPT service directly with the OCR text
        String response = GPTService.getChatGPTResponse(resultText);

        // You can optionally save the response if needed
        gptService.saveChatGPTResponse(response);

        return response;
    }
}
