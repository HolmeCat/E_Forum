package com.Springboot.service;

import com.Springboot.domain.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> selectTenRecommend();//查找10个的推荐结果，按照打分降序，序号降序
    Page<Product> selectAllByName(String name, Integer page, Integer size);//实现对作品名的模糊查询
    Page<Product> selectAllByTypeOrderByScore(String type,Integer page,Integer size);//实现对特定分类的排行榜查询，按照打分降序
    Page<Product> selectAll(String name,String introduction,String author,String type,String style,Integer page,Integer size);//对作品的综合模糊查询
    boolean insertOne(String name,String introduction,String author,String type,String style);//添加作品
}
