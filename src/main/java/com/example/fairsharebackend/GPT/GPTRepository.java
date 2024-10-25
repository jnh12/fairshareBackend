package com.example.fairsharebackend.GPT;

import com.example.fairsharebackend.Text.OCRResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GPTRepository extends MongoRepository<GPTResponse, String> {
    long countByDeviceUUID(String deviceUUID);

    Optional<GPTResponse> findTopByDeviceUUIDOrderByFsId(String deviceUUID);

}