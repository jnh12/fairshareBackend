package com.example.fairsharebackend.Text;

import com.example.fairsharebackend.GPT.GPTResponse;
import com.example.fairsharebackend.GPT.GPTResponseRequest;
import com.example.fairsharebackend.GPT.GPTService;
import com.example.fairsharebackend.Image.OCRImage;
import com.example.fairsharebackend.Image.OCRImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

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
