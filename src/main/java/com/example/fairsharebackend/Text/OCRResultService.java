package com.example.fairsharebackend.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OCRResultService {

    @Autowired
    private OCRResultRepository ocrResultRepository;

    public OCRResult saveOCRResult(String resultText) {
        OCRResult ocrResult = new OCRResult();
        ocrResult.setResultText(resultText);
        return ocrResultRepository.save(ocrResult);
    }

    public Optional<OCRResult> getOCRResult(String id) {
        return ocrResultRepository.findById(id);
    }
}
