package com.example.fairsharebackend.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ocr")
public class OCRImageController {

    @Autowired
    private OCRImageService ocrImageService;


    @PostMapping("/saveImage")
    public OCRImage saveOCRImage(@RequestBody OCRImageRequest request) {
        return ocrImageService.saveOCRImage(request.getDeviceUUID(), request.getImageData());
    }

}
