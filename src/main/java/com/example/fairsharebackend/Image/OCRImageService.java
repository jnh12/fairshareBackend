package com.example.fairsharebackend.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OCRImageService {

    @Autowired
    private OCRImageRepository ocrImageRepository;  // Corrected to match repository name

    public OCRImage saveOCRImage(byte[] resultImage) {
        OCRImage ocrImage = new OCRImage();
        ocrImage.setImageData(resultImage);  // Set the image data
        return ocrImageRepository.save(ocrImage);
    }

    public Optional<OCRImage> getOCRImage(String id){
        return ocrImageRepository.findById(id);
    }


}
