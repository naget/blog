package com.blog.repository;

import com.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t on 2016/10/31.
 */

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> ,JpaSpecificationExecutor{
    Article save(Article article);

    List<Article> findByUser_id(Long user_id);

    List<Article> findByTitle(String title);
    @Query("SELECT a from Article a where category_id=?1")
    Page<Article> findByCategoryId(Long category_id, Pageable pageable);
    @Query("select a from Article a  where a.content like concat('%',:content,'%') or a.title like concat('%',:content,'%')")
    Page<Article> findPersonally(@Param("content")String content, Pageable pageable);

//    @Query("select a from  Atricle a where user_id = ?1")
//    List<Article> findByUserId(Long id);



}
