package com.project.reviewservice;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.service.image.ImageService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SpringBootTest //통합테스트임을 의미 (통합테스트 = 실제 서버를 띄운다. 스프링 컨테이너가 일을 한다)
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Test
    @Order(1)
    public void 이미지_저장_테스트() throws IOException {

        //given - 두개의 여러 파일을 저장하는 작업을 테스트
        String filename1 = "file1.png";
        String filename2 = "fine2.jpg";
        byte[] content1 = filename1.getBytes();
        byte[] content2 = filename2.getBytes();
        int reviewNo = 1;

        //여기까지 ! 사용자가 2개의 파일을 화면에서 업로드했음을 의미한다.
        //Mock : 가짜라는 뜻으로 테스트할때 임의의 가짜 파일을 생성한다.
        // 깊이 알 필요는 없습니다. GPT한테 물어보고 따라서 작성하면 됩니다
        MultipartFile file1 = new MockMultipartFile("file" , filename1 , "image/png" , content1);
        MultipartFile file2 = new MockMultipartFile("file" , filename2 , "image/jpeg" , content2);

        Image savedImage1 = imageService.saveImage(file1 , reviewNo);
        Image savedImage2 = imageService.saveImage(file2 , reviewNo);

        Assertions.assertThat(savedImage1.getImageName()).isEqualTo(filename1);
        Assertions.assertThat(savedImage2.getImageName()).isEqualTo(filename2);
    }

    @Test
    @Order(2)
    public void 이미지_조회_테스트() {

        List<Image> images = imageService.getImagesByReviewNo(1);

        Assertions.assertThat(images.size()).isNotEqualTo(0);

        for(Image image : images) {
            System.out.println(image.toString());
        }

    }
}
