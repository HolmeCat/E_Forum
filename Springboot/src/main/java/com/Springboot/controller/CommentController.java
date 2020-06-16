package com.Springboot.controller;

import com.Springboot.domain.Comment;
import com.Springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/comment")
public class CommentController { //评测表控制层，主要承接前后端
    @Autowired
    private CommentService commentService;
    @CrossOrigin
    @GetMapping(value = "/selectTenRecommend")
    public List<Comment> selectTenRecommend() {
        return commentService.selectTenRecommend();
    }//查找10个的推荐结果
    @CrossOrigin
    @GetMapping(value = "/selectAllByProductId")
    public Page<Comment> selectAllByProductId(@RequestParam("id") Integer id,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size) {//对相关作品号的评测查询
        return commentService.selectAllByProductId(id,page,size);
    }
    @CrossOrigin
    @GetMapping(value = "/selectAllByUserName")
    public Page<Comment> selectAllByUserName(@RequestParam("name") String name,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size) {//对相关用户的评测查询
        return commentService.selectAllByUserName(name,page,size);
    }
    @CrossOrigin
    @GetMapping("/insertOne")
    public boolean insertOne(@RequestParam("context") String context,
                             @RequestParam("score") Integer score,
                             @RequestParam("productId") Integer productId,
                             @RequestParam("userName") String userName){ //添加评测
        return commentService.insertOne(context,score,productId,userName);
    }
    @CrossOrigin
    @GetMapping("/deleteOne")
    public boolean deleteOne(@RequestParam("id") Integer id){ //删除评测
        return commentService.deleteOne(id);
    }
}
