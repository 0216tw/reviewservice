package com.project.reviewservice.repository.user;

import com.project.reviewservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.userId = :userId AND u.password = :password")
    User loginCheck(@Param("userId")String userId , @Param("password") String password);

    Optional<User> findByUserId(String userId);
    //JPA의 좋은점 : 메서드명만 잘짜면 쿼리를 대신 만들어줌
    // 예) findBy + 컬럼명을 잘 쓰면 , select * from 테이블 where user_id = :userId 가 가능
}
