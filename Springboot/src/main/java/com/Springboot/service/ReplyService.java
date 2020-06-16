package com.Springboot.service;

import com.Springboot.domain.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> selectAllByCommentId(Integer id); //实现对相关评测号的回复查询
    boolean insertOne(String context,Integer commentId,String userName); //添加回复
}
