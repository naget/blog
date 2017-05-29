package com.blog.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by tianfeng on 2017/5/20.
 */
@Data
@Table
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long authorId;
    @Transient
    String authorName;
    String actioner;
    String actionName;
    Long articleId;
    public Notification(){}
    public Notification(Long authorId,String actioner,String actionName,Long articleId){
        this.actioner=actioner;
        this.actionName=actionName;
        this.articleId=articleId;
        this.authorId=authorId;
    }
}
