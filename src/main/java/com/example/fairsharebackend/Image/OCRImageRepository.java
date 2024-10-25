package com.example.fairsharebackend.Image;
import com.example.fairsharebackend.Text.OCRResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OCRImageRepository extends MongoRepository<OCRImage, String> {
    long countByDeviceUUID(String deviceUUID);

    Optional<OCRImage> findTopByDeviceUUIDOrderByFsId(String deviceUUID);

}