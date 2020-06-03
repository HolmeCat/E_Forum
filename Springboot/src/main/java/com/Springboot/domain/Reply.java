package com.Springboot.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "Reply")
public class Reply { //回复表
    @Id //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //自增
    private Integer id; //回复号
    @Column(nullable = false)
    private String context; //回复内容
    @Column(nullable = false)
    private Date date; //日期
    @JoinColumn(name = "commentId")
    @ManyToOne() //多个回复对一个评测
    private Comment comment; //外键评测
    @JoinColumn(name = "userName")
    @OneToOne() //一个回复对一个用户
    private User user; //外键用户
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public Date getDate() {
        return date;
    }
    public void setContext(String context) {
        this.context = context;
    }
    public String getContext() {
        return context;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return user;
    }
    public void setComment(Comment comment) {
        this.comment = comment;
    }
    public Comment getComment() {
        return comment;
    }
}
