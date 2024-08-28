package com.project.reviewservice.service.image;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void saveImages(MultipartFile[] files, int reviewNo) throws IOException {

        for( MultipartFile file : files ) {

            if (file.isEmpty() || file.getOriginalFilename() == null) {
                continue; // 파일이 비어 있거나 파일 이름이 null이면 무시합니다.
            }

            String fileName = file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(fileName);
            Files.createDirectories(uploadPath);

            System.out.println("파일명 : " + file.getOriginalFilename());
            System.out.println("업로드 경로 : " + uploadPath.toString());
            System.out.println("파일 경로 : " + filePath.toString());

            file.transferTo(filePath.toFile());



            Image image = new Image();
            image.setImageName(fileName);
            //reviewImage 내부 경로만 저장
            image.setImagePath( uploadDir + fileName);
            image.setReviewNo(reviewNo);
            imageRepository.save(image);

        }


    }

    public List<Image> getImagesByReviewNo(int reviewNo) {
        return imageRepository.findByReviewNo(reviewNo);
    }
}
