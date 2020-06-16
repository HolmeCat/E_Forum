package com.Springboot.service.Impl;

import com.Springboot.domain.Comment;
import com.Springboot.domain.Product;
import com.Springboot.domain.User;
import com.Springboot.repository.CommentRepository;
import com.Springboot.repository.ProductRepository;
import com.Springboot.repository.ReplyRepository;
import com.Springboot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ProductRepository productRepository; //特别注入作品的数据库操作支持
    @Autowired
    private ReplyRepository replyRepository; //特别注入回复的数据库操作支持
    @Override
    public List<Comment> selectTenRecommend() {
        return commentRepository.findFirst10ByOrderByScoreDesc();
    }
    @Override
    public Page<Comment> selectAllByProductId(Integer id, Integer page, Integer size) {
        //分页管理
        Pageable pageable;
        //请求第page页
        pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "score");
        Comment comment = new Comment();//创建匹配器，即如何使用查询条件
        Product product = new Product();
        product.setId(id);
        comment.setProduct(product);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("productId",ExampleMatcher.GenericPropertyMatchers.exact()); //精准查询
        exampleMatcher = exampleMatcher.withIgnorePaths("id","context","score","date","userName");
        //创建实例
        Example<Comment> example = Example.of(comment,exampleMatcher);
        return commentRepository.findAll(example,pageable);
    }
    @Override
    public Page<Comment> selectAllByUserName(String name, Integer page, Integer size) {
        //分页管理
        Pageable pageable;
        //请求第page页
        pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "date");
        Comment comment = new Comment();//创建匹配器，即如何使用查询条件
        User user = new User();
        user.setName(name);
        comment.setUser(user);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("userName",ExampleMatcher.GenericPropertyMatchers.exact()); //精准查询
        exampleMatcher = exampleMatcher.withIgnorePaths("id","context","score","date","productId");
        //创建实例
        Example<Comment> example = Example.of(comment,exampleMatcher);
        return commentRepository.findAll(example,pageable);
    }
    @Override
    public boolean insertOne(String context, Integer score, Integer productId, String userName) {
        Comment comment = new Comment();
        comment.setContext(context);
        comment.setScore(score);
        //外键封装进入对象
        Product product = new Product();
        product.setId(productId);
        User user = new User();
        user.setName(userName);
        comment.setProduct(product);
        comment.setUser(user);
        //生成当日日期存入，注意类型
        Date date = new Date(System.currentTimeMillis());
        comment.setDate(date);
        //最后执行
        commentRepository.save(comment);
        //再业务逻辑，更新目标作品的分数，执行更新计算方法
        updateProductScore(productId);
        return true;
    }
    @Override
    public boolean deleteOne(Integer id) {
        //先找到目标作品号
        Integer productId = commentRepository.findById(id).orElse(null).getProduct().getId();
        //然后删除所有相关回复，再删除该评论
        replyRepository.deleteAllByCommentId(id);
        commentRepository.deleteById(id);
        //再业务逻辑，更新目标作品的分数，执行更新计算方法
        updateProductScore(productId);
        return true;
    }
    private void updateProductScore(Integer id){ //更新目标作品的分数计算，输入作品id
        //找到目标作品对象
        Product product = productRepository.findById(id).orElse(null);
        //准备计算目标作品分数
        Integer result;
        //查出所有评测的分数总和，然后求平均
        result = commentRepository.updateProductScore(id);
        //如果说result是null的话，就设为0
        if(result == null) result = 0;
        //最后写入对象
        product.setScore(result);
        productRepository.save(product);
        return; //更新完毕
    }
}
