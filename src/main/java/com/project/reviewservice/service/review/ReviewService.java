package com.project.reviewservice.service.review;

import com.project.reviewservice.domain.image.Image;
import com.project.reviewservice.domain.review.Review;
import com.project.reviewservice.domain.review.ReviewClickEvent;
import com.project.reviewservice.repository.review.ReviewClickEventRepository;
import com.project.reviewservice.repository.review.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewClickEventRepository reviewClickEventRepository;

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
        return reviewRepository.findById(reviewNo).orElse(null);
    }


    //리뷰 저장
    public int saveReviewAndReturnReviewNo(String title, String content, String reviewerId) {

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
    public List<Review> findReviewsBySearch(String search) {
        return reviewRepository.findReviewsBySearch(search);
    }


    public List<Review> findReviewsByDay(String yyyymmdd) {
        return reviewRepository.findReviewsByDay(yyyymmdd);
    }


    @Scheduled(cron = "0,30 * * * * *") //cron : 메서드 실행 시점을 지정할 수 있다.
    //앞에서부터 초 분 시 일 월 요일 세팅가능 , *은 "모든" 이라는 의미
    //자세한 건 직접 공부해보세욤~!
    public void saveReviewClickEventPerOneMinute() {

        System.out.println("리뷰 클릭 사용자 로그 DB 적재를 실행합니다.");

        Date maxDate = reviewClickEventRepository.findReviewByMaxDate(); //리뷰가 등록된 최근 날짜를 가져온다.

        //로그 파일을 가져온다.
        String logFilePath = "logs/user_review_click_event.log"; // 로그 파일 경로 지정

        //날짜 포맷을 세팅한다.
        DateTimeFormatter LOG_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {

            BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
            String line;

            while ((line = reader.readLine()) != null) { //로그파일을 한줄씩 읽는다.
                //2024-08-29 00:37:00 - admin - 148 review click 이런 형태로 한줄씩 나옴

                String[] datas = line.split(" - ");

                // 2024-08-29 00:35:16 admin 145 review click 이런식으로 datas 에 하나씩 들어있음
                // 예) datas[0] = "2024-08-29 00:35:16"
                // 예) datas[1] = "admin"
                // 예) datas[2] = "145 review click"
                String dateTime = datas[0];
                String username = datas[1];
                int reviewNo = Integer.parseInt(datas[2].split(" ")[0]);  //145

                // 이제 datas[0] 을 기준으로 db에 저장된 마지막 데이터보다 이후이면 insert 할거임 (아래 부분은 GPT 쓴겁니다)

                // 문자열을 LocalDateTime으로 변환 ( =>  문자열을 날짜로 바꿈 , 특정 형식으로 yyyy-MM-dd HH:mm:ss )
                LocalDateTime logDateTime = LocalDateTime.parse(dateTime, LOG_DATE_FORMATTER);

                // Date 객체를 LocalDateTime으로 변환 (같은 타임존 사용) => DB에서 가져온 Date로 똑같은 형태로 만들어서 비교할 수 있도록 함
                LocalDateTime dbDateTime = maxDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                // 로그의 날짜가 DB에 저장된 날짜보다 이후인 경우에만 처리
                if (logDateTime.isAfter(dbDateTime)) {
                    // 이벤트 생성 및 리스트에 추가
                    ReviewClickEvent event = new ReviewClickEvent();
                    event.setUserName(username);
                    event.setReviewNo(reviewNo);
                    event.setRegDate(new Date()); // LocalDateTime을 Date로 변환

                    ReviewClickEvent enrolledEvent = reviewClickEventRepository.save(event);
                    System.out.println(enrolledEvent.toString() + "로 등록되었습니다");
                }
            }
            System.out.println("리뷰 클릭 사용자 로그 DB 적재를 종료합니다.");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
