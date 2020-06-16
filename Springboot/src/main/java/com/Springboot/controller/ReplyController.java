package com.Springboot.controller;

import com.Springboot.domain.Reply;
import com.Springboot.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/reply")
public class ReplyController { //回复表控制层，主要承接前后端
    @Autowired
    private ReplyService replyService;
    @CrossOrigin
    @GetMapping(value = "/selectAllByCommentId")
    public List<Reply> selectAllByCommentId(@RequestParam("id") Integer id){//对相关评测号的回复查询
        return replyService.selectAllByCommentId(id);
    }
    @CrossOrigin
    @GetMapping("/insertOne")
    public boolean insertOne(@RequestParam("context") String context,
                             @RequestParam("commentId") Integer commentId,
                             @RequestParam("userName") String userName){ //添加回复
        return replyService.insertOne(context,commentId,userName);
    }
}
