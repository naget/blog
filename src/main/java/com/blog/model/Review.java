package com.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.util.ArrayList;
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
    Timestamp time;
    String content;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    Article article;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REFRESH)
    @JoinColumn(name = "review_id")
    Review review;
    @OneToMany(mappedBy = "review",fetch = FetchType.EAGER)
    List<Review> reviewList=new ArrayList<Review>();
}
