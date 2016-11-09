package com.blog.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Data
@Table(name="user")

public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    BigInteger stu_id;
    String names;
    String grade;
    String login_name;
    String pwd;
    int groups;
    int isleader;
    BigInteger record_id;
    int sex;
    String phone;
//    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
//
//    private List<Article> articleList=new ArrayList<Article>();
}
