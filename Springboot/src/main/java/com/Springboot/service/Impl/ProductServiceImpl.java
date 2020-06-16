package com.Springboot.service.Impl;

import com.Springboot.domain.Product;
import com.Springboot.repository.ProductRepository;
import com.Springboot.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Override
    public List<Product> selectTenRecommend() {
        return productRepository.findFirst10ByOrderByScoreDesc();
    }
    @Override
    public Page<Product> selectAllByName(String name, Integer page, Integer size) {
        //分页管理
        Pageable pageable;
        //请求第page页
        pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "id");
        Product product = new Product();//创建匹配器，即如何使用查询条件
        product.setName(name);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        exampleMatcher = exampleMatcher.withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains()); //模糊查询
        exampleMatcher = exampleMatcher.withIgnorePaths("id","introduction","author","poster","type","style","score");
        //创建实例
        Example<Product> example = Example.of(product,exampleMatcher);
        return productRepository.findAll(example,pageable);
    }
    @Override
    public Page<Product> selectAllByTypeOrderByScore(String type, Integer page, Integer size) {
        //分页管理
        Pageable pageable;
        //请求第page页
        pageable = PageRequest.of(page-1, size, Sort.Direction.DESC, "score");
        Product product = new Product();//创建匹配器，即如何使用查询条件
        product.setType(type);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();
        if(!type.equals("全部"))
            exampleMatcher = exampleMatcher.withMatcher("type",ExampleMatcher.GenericPropertyMatchers.exact()); //精准查询
        else
            exampleMatcher = exampleMatcher.withIgnorePaths("type");
        exampleMatcher = exampleMatcher.withIgnorePaths("id","introduction","author","poster","name","style","score");
        //创建实例
        Example<Product> example = Example.of(product,exampleMatcher);
        return productRepository.findAll(example,pageable);
    }
    @Override
    public Page<Product> selectAll(String name, String introduction, String author, String type, String style,Integer page,Integer size) {
        //分页管理
        Pageable pageable;
        //请求第page页
        pageable = PageRequest.of(page-1, size);
        Product product = new Product();//创建匹配器，即如何使用查询条件
        product.setName(name);
        product.setIntroduction(introduction);
        product.setAuthor(author);
        product.setType(type);
        product.setStyle(style);
        ExampleMatcher exampleMatcher = ExampleMatcher.matching();//四种字段的模糊查询
        if(!name.equals(""))
            exampleMatcher = exampleMatcher.withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
        else exampleMatcher = exampleMatcher.withIgnorePaths("name");
        if(!introduction.equals(""))
            exampleMatcher = exampleMatcher.withMatcher("introduction",ExampleMatcher.GenericPropertyMatchers.contains());
        else exampleMatcher = exampleMatcher.withIgnorePaths("introduction");
        if(!author.equals(""))
            exampleMatcher = exampleMatcher.withMatcher("author",ExampleMatcher.GenericPropertyMatchers.contains());
        else exampleMatcher = exampleMatcher.withIgnorePaths("author");
        if(!style.equals(""))
            exampleMatcher = exampleMatcher.withMatcher("style",ExampleMatcher.GenericPropertyMatchers.contains());
        else exampleMatcher = exampleMatcher.withIgnorePaths("style");
        if(!type.equals("全部"))  //类型字段是否精准查询
            exampleMatcher = exampleMatcher.withMatcher("type",ExampleMatcher.GenericPropertyMatchers.exact()); //精准查询
        else exampleMatcher = exampleMatcher.withIgnorePaths("type");
        exampleMatcher = exampleMatcher.withIgnorePaths("id","score");
        //创建实例
        Example<Product> example = Example.of(product,exampleMatcher);
        return productRepository.findAll(example,pageable);
    }
    @Override
    public boolean insertOne(String name, String introduction, String author, String type, String style) {
        Product product = new Product();
        product.setName(name);
        product.setIntroduction(introduction);
        product.setAuthor(author);
        product.setType(type);
        product.setStyle(style);
        product.setScore(0);
        productRepository.save(product);
        return true;
    }
}
