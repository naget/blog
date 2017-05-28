package com.blog.model;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t on 2017/4/21.
 */
@Data
@Entity
public class Roles {
    @Id
    @GeneratedValue
    private Long id;


    private String name;
    private String mark;

    @ManyToMany()
    @JoinTable(name = "role_user",joinColumns = {@JoinColumn(name = "role_id")}
            ,inverseJoinColumns ={@JoinColumn(name = "user_id")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> users = new ArrayList<>();
}
