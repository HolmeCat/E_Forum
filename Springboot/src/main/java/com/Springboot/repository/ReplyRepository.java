package com.Springboot.repository;

import com.Springboot.domain.Comment;
import com.Springboot.domain.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReplyRepository extends JpaRepository<Reply,Integer> {
    List<Reply> findAllByCommentId(Integer id);
    void deleteAllByCommentId(Integer id);
}
