package com.example.fairsharebackend.Text;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface OCRResultRepository extends MongoRepository<OCRResult, String> {
    long countByDeviceUUID(String deviceUUID);

    Optional<OCRResult> findTopByDeviceUUIDOrderByFsIdDesc(String deviceUUID);
}