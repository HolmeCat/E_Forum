package com.Springboot.controller;

import com.Springboot.domain.Product;
import com.Springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/product")
public class ProductController {//作品表控制层，主要承接前后端
    @Autowired
    private ProductService productService;
    @CrossOrigin
    @GetMapping(value = "/selectTenRecommend")
    public List<Product> selectTenRecommend(){
        return productService.selectTenRecommend();
    }//查找10个的推荐结果
    @CrossOrigin
    @GetMapping("/selectAllByName")
    public Page<Product> selectAllByName(@RequestParam("name")String name,
                                         @RequestParam("page")Integer page,
                                         @RequestParam("size")Integer size){//对作品名的模糊查询
        return productService.selectAllByName(name,page,size);
    }
    @CrossOrigin
    @GetMapping("/selectAllByTypeOrderByScore")
    public Page<Product> selectAllByTypeOrderByScore(@RequestParam("type")String type,
                                                     @RequestParam("page")Integer page,
                                                     @RequestParam("size")Integer size){//对特定分类的排行榜查询
        return productService.selectAllByTypeOrderByScore(type,page,size);
    }
    @CrossOrigin
    @GetMapping("/selectAll")
    public Page<Product> selectAll(@RequestParam("name") String name,
                                   @RequestParam("introduction") String introduction,
                                   @RequestParam("author") String author,
                                   @RequestParam("type") String type,
                                   @RequestParam("style") String style,
                                   @RequestParam("page")Integer page,
                                   @RequestParam("size")Integer size){ //对作品的综合模糊查询
        return productService.selectAll(name,introduction,author,type,style,page,size);
    }
    @CrossOrigin
    @GetMapping("/insertOne")
    public boolean insertOne(@RequestParam("name") String name,
                             @RequestParam("introduction") String introduction,
                             @RequestParam("author") String author,
                             @RequestParam("type") String type,
                             @RequestParam("style") String style){ //添加作品
        return productService.insertOne(name,introduction,author,type,style);
    }
}
