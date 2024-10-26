package com.example.fairsharebackend.Image;
import com.example.fairsharebackend.Text.OCRResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface OCRImageRepository extends MongoRepository<OCRImage, String> {
    long countByDeviceUUID(String deviceUUID);

    Optional<OCRImage> findTopByDeviceUUIDOrderByFsIdDesc(String deviceUUID);

    List<OCRImage> findAllByDeviceUUID(String deviceUUID);

    Optional<OCRImage> findByFsId(String fsId);

}