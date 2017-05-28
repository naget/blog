package com.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by t on 2017/4/17.
 */
@Entity
@Data
public class ArticleContent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String content;
    public ArticleContent(String content){
        this.content = content;
    }
    public ArticleContent(){}
}
