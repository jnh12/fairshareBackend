package com.example.fairsharebackend.Image;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "ocrImages")
public class OCRImage {
    @Id
    private String id;
    private String fsId;
    private String deviceUUID;
    private byte[] imageData;
}
