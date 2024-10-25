package com.example.fairsharebackend.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OCRResultService {

    @Autowired
    public OCRResultRepository ocrResultRepository;

    public OCRResult saveOCRResult(String deviceUUID, String resultText) {
        OCRResult ocrResult = new OCRResult();
        ocrResult.setResultText(resultText);
        ocrResult.setDeviceUUID(deviceUUID);

        long count = ocrResultRepository.countByDeviceUUID(deviceUUID);
        ocrResult.setFsId(String.valueOf(count + 1));

        return ocrResultRepository.save(ocrResult);
    }

    public Optional<OCRResult> findLatestOCRResultByDeviceUUID(String deviceUUID) {
        return ocrResultRepository.findTopByDeviceUUIDOrderByFsId(deviceUUID);
    }

}

