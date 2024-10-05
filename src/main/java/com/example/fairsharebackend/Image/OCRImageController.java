package com.example.fairsharebackend.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/ocr")
public class OCRImageController {

    @Autowired
    private OCRImageService ocrImageService;

    @PostMapping("/saveImage")
    public OCRImage saveOCRImage(@RequestBody byte[] resultImage) {
        return ocrImageService.saveOCRImage(resultImage);
    }

    @GetMapping("/getImage/{id}")
    public Optional<OCRImage> getOCRImage(@PathVariable String id) {
        return ocrImageService.getOCRImage(id);
    }


}
