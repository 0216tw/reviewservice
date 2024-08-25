package com.project.reviewservice.controller.review;

import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.service.user.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewDataController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("reviews/{reviewNo}")
    public ResponseEntity<Review> findReviewByReviewNo(@PathVariable(name="reviewNo") Integer reviewNo) {

        Review review = reviewService.findReviewByReviewNo(reviewNo);
        if (review != null) {
            return ResponseEntity.ok(review);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
