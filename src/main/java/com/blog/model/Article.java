package com.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

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

    public Article(Long contentId, String title, String content, Timestamp pubtime, int readNumber, int reviewNumber, int topNumber, int bottomNumber, int hotIndex, User user, Category category) {
        this.contentId = contentId;
        this.title = title;
        this.content = content;
        this.pubtime = pubtime;
        this.readNumber = readNumber;
        this.reviewNumber = reviewNumber;
        this.topNumber = topNumber;
        this.bottomNumber = bottomNumber;
        this.hotIndex = hotIndex;
        this.user = user;
        this.category = category;
    }
    public Article(){}

    Long contentId;
    String title;
    String content;
    Timestamp pubtime;

    int readNumber;
    int reviewNumber;
    int topNumber;
    int bottomNumber;
    @Column(name = "hot_index")
    int hotIndex;
//    @Column(name = "user_id")
//    String userId;
    @Transient //不映射数据库
    String author;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH})
    @JoinColumn(name="user_id")//外键，自动生成
    User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE})
    @JoinColumn(name = "category_id")
    Category category;
//    @OneToMany(mappedBy = "article")
//    List<Review> reviewList=new ArrayList<Review>();

}
