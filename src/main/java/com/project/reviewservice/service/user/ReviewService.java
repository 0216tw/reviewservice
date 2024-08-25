package com.project.reviewservice.service.user;

import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    //전체 게시물 조회
    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    //특정 게시물 조회
    public Review findReviewByReviewNo(int reviewNo) {

        /*
        Optional<Review> foundReview = reviewRepository.findById(reviewNo);

        if(foundReview.isPresent()) {
            return foundReview.get();
        } else {
            return null;
        }
        */

        return reviewRepository.findById(reviewNo).orElse(null) ;
    }

}
