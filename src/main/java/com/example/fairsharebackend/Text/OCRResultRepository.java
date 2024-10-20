package com.example.fairsharebackend.Text;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface OCRResultRepository extends MongoRepository<OCRResult, String> {
    long countByDeviceUUID(String deviceUUID);
}