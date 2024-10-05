package com.example.fairsharebackend.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ocr")
public class OCRResultController {

    @Autowired
    private OCRResultService ocrResultService;

    @PostMapping("/saveText")
    public OCRResult saveOCRResult(@RequestBody String resultText) {
        return ocrResultService.saveOCRResult(resultText);
    }

    @GetMapping("/getText/{id}")
    public Optional<OCRResult> getOCRResult(@PathVariable String id) {
        return ocrResultService.getOCRResult(id);
    }
}
