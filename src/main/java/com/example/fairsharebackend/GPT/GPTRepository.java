package com.example.fairsharebackend.GPT;

import com.example.fairsharebackend.Text.OCRResult;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GPTRepository extends MongoRepository<GPTResponse, String> {



}