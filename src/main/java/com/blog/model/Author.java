package com.blog.model;

import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * Created by t on 2017/3/14.
 */
@Data
public class Author {
    Long id;
    String name;
    String group;
    String email;
    String grade;
    String articleNumber;
    String head;
    int hotIndex;
    Page<Article> latestArticles;
    public Author(){}

    public Author(Long id,String name, String group, String email, String grade, String articleNumber, int hotIndex,Page<Article> articles,String head) {
        this.id=id;
        this.name = name;
        this.group = group;
        this.email = email;
        this.grade = grade;
        this.articleNumber = articleNumber;
        this.hotIndex = hotIndex;
        this.latestArticles=articles;
        this.head=head;
    }
}
