package com.example.fairsharebackend.Text;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocr")
public class OCRResultController {

    @Autowired
    private OCRResultService ocrResultService;


    @PostMapping("/saveText")
    public OCRResult saveOCRResult(@RequestBody OCRResultRequest request) {
        return ocrResultService.saveOCRResult(request.getDeviceUUID(), request.getResultText());
    }





}
