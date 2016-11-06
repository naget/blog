package com.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Table(name = "article")

@Data
//@AllArgsConstructor
public class Article{
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    Long id;
    String title;
    String content;
    Timestamp pubtime;

    Integer readNumber;
    Integer reviewNumber;
    Integer topNumber;
    Integer bottomNumber;



    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name="user_id")//外键，自动生成
    User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    Category category;
    @OneToMany(mappedBy = "article")
    List<Review> reviewList=new ArrayList<Review>();

}
