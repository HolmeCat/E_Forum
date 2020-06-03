package com.Springboot.domain;

import javax.persistence.*;

@Entity
@Table(name = "Product")
public class Product { //作品表
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增
    private Integer id; //作品号
    @Column(nullable = false)
    private String name; //作品名
    @Column
    private String introduction; //作品简介
    @Column
    private String author; //作者
    @Column
    private byte[] poster; //海报
    @Column(nullable = false)
    private String type; //分类
    @Column(nullable = false)
    private String style; //风格
    @Column(nullable = false)
    private Integer score; //评分
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getAuthor() {
        return author;
    }
    public void setPoster(byte[] poster) {
        this.poster = poster;
    }
    public byte[] getPoster() {
        return poster;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public void setStyle(String style) {
        this.style = style;
    }
    public String getStyle() {
        return style;
    }
    public void setScore(Integer score) {
        this.score = score;
    }
    public Integer getScore() {
        return score;
    }
}
