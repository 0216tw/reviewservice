package com.project.reviewservice.controller.review;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.service.image.ImageService;
import com.project.reviewservice.service.user.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
public class ReviewDataController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ImageService imageService;

    @GetMapping("reviews/{reviewNo}") // 특정 게시물 조회
    public ResponseEntity<HashMap<String , Object>> findReviewByReviewNo(@PathVariable(name="reviewNo") Integer reviewNo) {

        Review review = reviewService.findReviewByReviewNo(reviewNo);
        List<Image> images = imageService.getImagesByReviewNo(reviewNo);

        //두 데이터를 하나로 합쳐서 보내기 (HashMap 활용)
        //String에는 key , Object 에는 data
        //Object는 모든 클래스의 최상위 객체! 다형성 원리를 이용해 어떤 데이터든 대입할 수 있음
        //Object <- Review 클래스 업캐스팅
        //Object <- List 클래스 업캐스팅
        HashMap<String , Object> data = new HashMap<>();
        data.put("reivew" , review);
        data.put("images" , images);

        if (review != null) {
            return ResponseEntity.ok(data);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
