package com.project.reviewservice.repository.review;

import com.project.reviewservice.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Integer> {


    //이번에는 네이티브 쿼리로 한번 해봤습니다.
    @Query(value = "SELECT * FROM TB_REVIEW WHERE REVIEW_CONTENT LIKE '%' || :search || '%' " , nativeQuery = true)
    List<Review> findReviewsBySearch(@Param(value="search") String search);

    @Query(value = "SELECT * FROM TB_REVIEW WHERE TO_CHAR(REVIEW_DATE , 'YYYYMMDD') = :yyyymmdd" , nativeQuery = true)
    List<Review> findReviewsByDay(@Param(value = "yyyymmdd") String yyyymmmdd);

}
