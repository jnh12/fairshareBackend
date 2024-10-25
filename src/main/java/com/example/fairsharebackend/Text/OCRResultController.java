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

    @Autowired
    private GPTService GPTService;

    @Autowired
    private GPTService gptService;

    @Autowired
    private OCRImageService ocrImageService;

    @PostMapping("/saveText")
    public OCRResult saveOCRResult(@RequestBody OCRResultRequest request) {
        return ocrResultService.saveOCRResult(request.getDeviceUUID(), request.getResultText());
    }

    @PostMapping("/parseText")
    public String parseOCRText(@RequestBody GPTResponseRequest request) throws Exception {
        String resultText = request.getResultText();
        String deviceUUID = request.getDeviceUUID();

        // Call the ChatGPT service with the OCR text
        String response = GPTService.getChatGPTResponse(resultText);

        // Save the response along with the UUID
        gptService.saveChatGPTResponse(response, deviceUUID);

        return response;
    }

    @GetMapping("/getParsedTextAndImage")
    public ParsedAndImageRequest getParsedTextAndImage(@RequestBody String deviceUUID) {
        ParsedAndImageRequest parsedAndImageRequest = new ParsedAndImageRequest();

        Optional<GPTResponse> GPTResponceOptional = gptService.findLatestGPTResponseByDeviceUUID(deviceUUID);
        Optional<OCRImage> ocrImageOptional = ocrImageService.findLatestOCRImageByDeviceUUID(deviceUUID);

        if (GPTResponceOptional.isPresent() && ocrImageOptional.isPresent()) {
            GPTResponse gptResponse = GPTResponceOptional.get();
            OCRImage ocrImage = ocrImageOptional.get();

            parsedAndImageRequest.setGPTResponse(gptResponse);
            parsedAndImageRequest.setImageData(ocrImage.getImageData());
        } else {
            if (GPTResponceOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GPT Response not found for deviceUUID: " + deviceUUID);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OCR Image not found for deviceUUID: " + deviceUUID);
        }

        return parsedAndImageRequest;
    }



}
