package com.Springboot.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Comment")
public class Comment {
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增
    private Integer id; //评测号
    @Column(nullable = false)
    private String context; //评测内容
    @Column(nullable = false)
    private Integer score; //评分
    @Column(nullable = false)
    private Date date; //日期
    @JoinColumn(name = "productId")
    @ManyToOne() //多个评测对一个作品
    private Product product; //外键作品
    @JoinColumn(name = "userName")
    @OneToOne() //一个评测对一个用户
    private User user; //外键用户
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getContext() {
        return context;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getScore() {
        return score;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Product getProduct() {
        return product;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
}
