package com.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Table(name = "category")
@Data
public class Category implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "type_name")
    String typeName;
//    @OneToMany(mappedBy = "category",fetch = FetchType.EAGER)
//    List<Article> articleList=new ArrayList<Article>();
}
