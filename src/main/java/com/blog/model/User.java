package com.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Data
@Table(name="user")
public class User implements Serializable,UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    BigInteger stuId;
    String names;
    String grade;
    String email;
    String loginName;
    String pwd;
    String groups;
    String head;
    int isLeader;
    BigInteger recordId;
    int sex;
    String phone;
    String text;//自我介绍
    int hotIndex;//热度
    @ManyToMany(mappedBy = "users")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Roles> roles = new ArrayList<Roles>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        List<Roles> roles = this.getRoles();
        for (Roles role:roles){
            auths.add(new SimpleGrantedAuthority(role.getMark()));
            System.out.println(role.getMark());
        }
        return auths;
    }

    @Override
    public String getPassword() {
        return pwd;
    }

    @Override
    public String getUsername() {
        return names;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



//    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
//
//    private List<Article> articleList=new ArrayList<Article>();
}
