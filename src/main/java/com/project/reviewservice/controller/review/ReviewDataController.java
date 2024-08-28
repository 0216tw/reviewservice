package com.project.reviewservice.controller.review;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.service.image.ImageService;
import com.project.reviewservice.service.review.ReviewService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


@RestController
public class ReviewDataController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ImageService imageService;

    //로깅 세팅
    private static final Logger userReviewClickEvent = LoggerFactory.getLogger("com.project.reviewservice.controller.review.ReviewDataController.findReviewByReviewNo");
    private static final Logger userReviewSearchEvent = LoggerFactory.getLogger("com.project.reviewservice.controller.review.ReviewDataController.findReviewBySearch");



    @GetMapping("reviews/{reviewNo}") // 특정 게시물 조회
    public ResponseEntity<HashMap<String , Object>> findReviewByReviewNo(@PathVariable(name="reviewNo") Integer reviewNo
                                                                       , HttpSession session) {

        userReviewClickEvent.info(session.getAttribute("username") + " - " + reviewNo + " review click");

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

    @GetMapping("reviews/search/{search}") // 검색어 기준으로 게시물 조회
    public ResponseEntity<List<Review>> findReviewBySearch(@PathVariable(name="search") String search
                                                          , HttpSession session) {

        userReviewSearchEvent.info(session.getAttribute("username") + " - " + search + " search");

        List<Review> reviews = reviewService.findReviewsBySearch(search);
        return ResponseEntity.ok(reviews);
    }


    @PostMapping("/review/saveReview")
    public ResponseEntity<String> postReview(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("images") MultipartFile[] files,
            HttpSession session ,
            HttpServletResponse response) throws IOException {

        if(session.getAttribute("userId") == null) {
            // 로그인 페이지로 리다이렉트
            response.sendRedirect("/login");
            return ResponseEntity.status(302).body("Redirecting to login"); // 302: Found (Redirect)
        }

        try {
            // ReviewService를 사용하여 리뷰 저장하고 reviewNo를 반환
            String reviewerId = (String)session.getAttribute("userId");
            int reviewNo = reviewService.saveReviewAndReturnReviewNo(title, content , reviewerId);
            imageService.saveImages(files, reviewNo);

            return ResponseEntity.ok("success") ;
        } catch(IOException e) {
            e.printStackTrace();
            return ResponseEntity.ok("fail") ;
        }
    }

}
