package com.Springboot.repository;

import com.Springboot.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findFirst10ByOrderByScoreDesc();
    //对目标作品的评测打分求平均值
    @Query(value = "select avg(score) from comment where product_id = ?",nativeQuery = true)
    Integer updateProductScore(Integer id);
}
