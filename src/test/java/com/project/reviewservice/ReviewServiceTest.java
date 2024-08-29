package com.project.reviewservice;

import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.service.review.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;


    @Test
    public void 검색어_기준으로_데이터_검색하기() {

        String search = "테스트";
        List<Review> reviews = reviewService.findReviewsBySearch(search);

        for(Review review : reviews) {
            System.out.println(review);
        }
    }

    @Test
    public void 일자를_기준으로_데이터_검색하기() {

        String day = "20240825";
        List<Review> reviews = reviewService.findReviewsByDay(day);

        for(Review re : reviews) {
            System.out.println(re.toString());
        }
    }

    @Test
    public void 로그파일을_잘읽어오는지_테스트() {
        reviewService.saveReviewClickEventPerOneMinute();
    }
}
