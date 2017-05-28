package com.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Data
public class Review implements Serializable{
    @Id
    @GeneratedValue
    Long id;
    Date time;
    String content;
    Integer hasChild;
    Long childId;
    Long reviewerId;
    @Transient
    List<Review> childList;
    @Transient
    String reviewerName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    Article article;
    public Review(Date time,String content,Long reviewerId){
        this.time=time;
        this.content=content;
        this.reviewerId=reviewerId;
    }
    public Review(){}
//    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "review_id")
//    Review review;
//    @OneToMany(mappedBy = "review",fetch = FetchType.EAGER)
//    List<Review> reviewList=new ArrayList<Review>();
}
