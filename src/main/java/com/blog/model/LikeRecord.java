package com.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by t on 2017/3/16.
 */
@Entity
@Data
public class LikeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long articleId;
    Long likerId;
    public LikeRecord(Long likerId, Long articleId){
        this.articleId=articleId;
        this.likerId=likerId;
    }
    public LikeRecord(){}
}
