package com.example.fairsharebackend.Image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OCRImageService {

    @Autowired
    private OCRImageRepository ocrImageRepository;

    public OCRImage saveOCRImage(String deviceUUID, byte[] resultImage) {
        OCRImage ocrImage = new OCRImage();
        ocrImage.setDeviceUUID(deviceUUID);
        ocrImage.setImageData(resultImage);

        long count = ocrImageRepository.countByDeviceUUID(deviceUUID);
        ocrImage.setFsId(String.valueOf(count + 1));

        return ocrImageRepository.save(ocrImage);
    }

}
