package com.Springboot.service;

import com.Springboot.domain.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CommentService {
    List<Comment> selectTenRecommend();//查找10个的推荐结果，按照打分降序，序号降序
    Page<Comment> selectAllByProductId(Integer id,Integer page,Integer size);//实现对相关作品号的评测查询
    Page<Comment> selectAllByUserName(String name,Integer page,Integer size);//实现对相关用户的评测查询
    boolean insertOne(String context,Integer score,Integer productId,String userName); //添加评测
    boolean deleteOne(Integer id);//删除一条评测
}
