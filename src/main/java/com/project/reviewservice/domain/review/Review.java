package com.project.reviewservice.domain.review;

import jakarta.persistence.*;

import java.util.Date;

/*
CREATE TABLE TB_REVIEW (
    REVIEW_NO NUMBER PRIMARY KEY ,
    REVIEWER_ID VARCHAR2(12) NOT NULL ,
    REVIEW_TITLE VARCHAR2(500) NOT NULL ,
    REVIEW_CONTENT VARCHAR2(4000) NOT NULL ,
    REVIEW_DATE DATE
) ;
 */

@Entity
@Table(name = "tb_review")
public class Review {

    @Id
    @Column(name = "review_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동으로 PK을 관리해준다. +1전략이다.
    private int reviewNo;

    @Column(name = "reviewer_id")
    private String reviewerId;

    @Column(name = "review_title")
    private String reviewTitle;

    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "review_date")
    private Date reviewDate;

    public int getReviewNo() {
        return reviewNo;
    }

    public void setReviewNo(int reviewNo) {
        this.reviewNo = reviewNo;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewNo=" + reviewNo +
                ", reviewerId='" + reviewerId + '\'' +
                ", reviewTitle='" + reviewTitle + '\'' +
                ", reviewContent='" + reviewContent + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}
