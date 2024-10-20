package com.example.fairsharebackend.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OCRResultService {

    @Autowired
    private OCRResultRepository ocrResultRepository;

    public OCRResult saveOCRResult(String deviceUUID, String resultText) {
        OCRResult ocrResult = new OCRResult();
        ocrResult.setResultText(resultText);
        ocrResult.setDeviceUUID(deviceUUID);

        long count = ocrResultRepository.countByDeviceUUID(deviceUUID);
        ocrResult.setFsId(String.valueOf(count + 1));

        return ocrResultRepository.save(ocrResult);
    }
}

