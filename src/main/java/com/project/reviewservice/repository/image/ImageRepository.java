package com.project.reviewservice.repository.image;

import com.project.reviewservice.domain.image.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Integer> {

    /* 똑같은 쿼리를 작성하는 3가지 방법 */

    //규칙에 맞게 메서드명을 적어주면 쿼리를 자동 작성해줌
    //예) findByReviewNo -> SELECT * FROM TB_IMAGE where review_no = :reviewNo
    public List<Image> findByReviewNo(int reviewNo);

    //혹은 직접 쿼리 작성도 가능하다. 단, 이때 객체명명을 기준으로 작성한다.
    @Query("select m from Image m where m.reviewNo = :reviewNo")
    public List<Image> findByReviewNoUsingQuery(int reviewNo);

    //혹은 네이티브 쿼리를 사용하면 , 오라클 문법에 맞게 작성할 수 있다.
    @Query(value = "select * from tb_image where review_no = :reviewNo" , nativeQuery = true)
    public List<Image> findByReviewNoUsingNativeQuery(int reviewNo);
}
