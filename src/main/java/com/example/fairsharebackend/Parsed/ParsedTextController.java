package com.example.fairsharebackend.Parsed;

import com.example.fairsharebackend.GPT.GPTResponse;
import com.example.fairsharebackend.GPT.GPTResponseRequest;
import com.example.fairsharebackend.GPT.GPTService;
import com.example.fairsharebackend.Image.OCRImage;
import com.example.fairsharebackend.Image.OCRImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ocr")
public class ParsedTextController {


    @Autowired
    private GPTService gptService;

    @Autowired
    private OCRImageService ocrImageService;

    @PostMapping("/parseText")
    public String parseOCRText(@RequestBody GPTResponseRequest request) throws Exception {
        String resultText = request.getResultText();
        String deviceUUID = request.getDeviceUUID();

        // Call the ChatGPT service with the OCR text
        String response = gptService.getChatGPTResponse(resultText);

        // Save the response along with the UUID
        gptService.saveChatGPTResponse(response, deviceUUID);

        return response;
    }

    @GetMapping("/getParsedTextAndImage")
    public ParsedAndImageResponse getParsedTextAndImage(@RequestBody String deviceUUID) {
        ParsedAndImageResponse parsedAndImageResponse = new ParsedAndImageResponse();

        Optional<GPTResponse> GPTResponceOptional = gptService.findLatestGPTResponseByDeviceUUID(deviceUUID);
        Optional<OCRImage> ocrImageOptional = ocrImageService.findLatestOCRImageByDeviceUUID(deviceUUID);

        if (GPTResponceOptional.isPresent() && ocrImageOptional.isPresent()) {
            GPTResponse gptResponse = GPTResponceOptional.get();
            OCRImage ocrImage = ocrImageOptional.get();

            parsedAndImageResponse.setGPTResponse(gptResponse);
            parsedAndImageResponse.setImageData(ocrImage.getImageData());
        } else {
            if (GPTResponceOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "GPT Response not found for deviceUUID: " + deviceUUID);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OCR Image not found for deviceUUID: " + deviceUUID);
        }

        return parsedAndImageResponse;
    }

    @GetMapping("/getAllRecents")
    public List<ParsedAndImageResponse> getAllRecents(@RequestBody String deviceUUID) {

        List<ParsedAndImageResponse> parsedAndImageResponses = new ArrayList<>();

        for(int i = 1; i <= gptService.findCountByDeviceUUID(deviceUUID); i++){
            ParsedAndImageResponse parsedAndImageResponse = new ParsedAndImageResponse();
            Optional<GPTResponse> gptResponseOptional = gptService.findByFsId(String.valueOf(i));
            Optional<OCRImage> ocrImageOptional = ocrImageService.findByFsId(String.valueOf(i));

            parsedAndImageResponse.setGPTResponse(gptResponseOptional.get());
            parsedAndImageResponse.setImageData(ocrImageOptional.get().getImageData());

            parsedAndImageResponses.add(parsedAndImageResponse);
        }

        return parsedAndImageResponses;

    }
}
