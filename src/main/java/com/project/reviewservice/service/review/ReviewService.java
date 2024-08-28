package com.project.reviewservice.service.review;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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


    //리뷰 저장
    public int saveReviewAndReturnReviewNo(String title, String content , String reviewerId) {

        // 리뷰 객체 생성 및 저장
        Review review = new Review();
        review.setReviewTitle(title);
        review.setReviewContent(content);
        review.setReviewerId(reviewerId);

        // 리뷰를 저장하고 생성된 ID 반환
        Review savedReview = reviewRepository.save(review);
        return savedReview.getReviewNo();
    }



    //검색어 기반으로 게시물 조회
    public List<Review> findReviewsBySearch(String search) { return reviewRepository.findReviewsBySearch(search); }


    public List<Review> findReviewsByDay(String yyyymmdd) {
        return reviewRepository.findReviewsByDay(yyyymmdd);
    }


}
