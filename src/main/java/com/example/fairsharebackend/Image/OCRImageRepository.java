package com.example.fairsharebackend.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OCRImageRepository extends MongoRepository<OCRImage, String> {
}