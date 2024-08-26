package com.project.reviewservice.service.image;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Value("${image.upload.dir}")
    private String uploadDir;

    public Image saveImage(MultipartFile file, int reviewNo) throws IOException {
        String fileName = file.getOriginalFilename();
        Path uploadPath = Paths.get(uploadDir);
        Path filePath = uploadPath.resolve(fileName);

        Files.createDirectories(uploadPath);
        file.transferTo(filePath.toFile());

        Image image = new Image();
        image.setImageName(fileName);
        image.setImagePath(filePath.toString().substring(1));
        image.setReviewNo(reviewNo);

        return imageRepository.save(image);
    }

    public List<Image> getImagesByReviewNo(int reviewNo) {
        return imageRepository.findByReviewNo(reviewNo);
    }
}
