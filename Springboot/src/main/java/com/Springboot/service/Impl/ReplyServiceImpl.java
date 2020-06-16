package com.Springboot.service.Impl;

import com.Springboot.domain.Comment;
import com.Springboot.domain.Reply;
import com.Springboot.domain.User;
import com.Springboot.repository.ReplyRepository;
import com.Springboot.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;
    @Override
    public List<Reply> selectAllByCommentId(Integer id) {
        return replyRepository.findAllByCommentId(id);
    }
    @Override
    public boolean insertOne(String context, Integer commentId, String userName) {
        Reply reply = new Reply();
        reply.setContext(context);
        //外键封装进入对象
        Comment comment = new Comment();
        comment.setId(commentId);
        User user = new User();
        user.setName(userName);
        reply.setComment(comment);
        reply.setUser(user);
        //生成当日日期存入，注意类型
        Date date = new Date(System.currentTimeMillis());
        reply.setDate(date);
        //最后执行
        replyRepository.save(reply);
        return true;
    }
}
