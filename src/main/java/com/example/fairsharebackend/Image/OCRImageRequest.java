package com.example.fairsharebackend.Image;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OCRImageRequest {
    private String deviceUUID;
    private byte[] imageData;


}

