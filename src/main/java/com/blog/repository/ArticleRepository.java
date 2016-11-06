package com.blog.repository;

import com.blog.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t on 2016/10/31.
 */

@Repository
public interface ArticleRepository extends JpaRepository <Article,Long>   {
    Article save(Article article);
List<Article> findByUser_id(Long user_id);
    List<Article> findByTitle(String title);
//    Page<Article> getLatestArticle(Pageable pageable);
//    Page<Article>  get();

}
