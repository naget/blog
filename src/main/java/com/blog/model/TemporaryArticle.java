package com.blog.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.security.Timestamp;

/**
 * Created by t on 2016/10/31.
 */
@Entity
@Table(name="temporary_article")
@Data
public class TemporaryArticle implements Serializable{
    @Id
    @GeneratedValue
    Long id;
    String title;
    String content;
    Timestamp savetime;

}
