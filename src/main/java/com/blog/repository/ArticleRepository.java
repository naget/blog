package com.blog.repository;

import com.blog.model.Article;
import com.blog.model.Category;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by t on 2016/10/31.
 */

@Repository
//@CacheConfig(cacheNames = "article")
public interface ArticleRepository extends JpaRepository<Article, Long> ,JpaSpecificationExecutor{
    Article save(Article article);

    Article findById(Long id);
    List<Article> findByUser_id(Long user_id);
//    @Cacheable(key="#p0")

    @Query("SELECT count(a.id) from Article a where user_id=?1")
    Integer findArticleCountByUserId(Long id);
    List<Article> findByTitle(String title);
    @Query("SELECT  a from Article a where user_id=?1")
    Page<Article> findArticlesByAuthorId(Long authorId,Pageable pageable);

    @Query("SELECT a from Article a where category_id=?1")
    Page<Article> findByCategoryId(Long category_id, Pageable pageable);
    @Query("select a from Article a  where a.content like concat('%',:content,'%') or a.title like concat('%',:content,'%')")
    Page<Article> findPersonally(@Param("content")String content, Pageable pageable);
    @Query("SELECT a from Article a where user_id=?1 order by a.id desc")
    Page<Article> findLatestArticles(Long user_id,Pageable pageable);
    @Modifying
    @Transactional
    @Query("DELETE from Article a where a.id=?1")
    void deleteById(Long articleId);
//    @CachePut(key = "#p4")
    @Query("update Article a set a.title=?1,a.content=?2,a.category=?3 where a.id=?4")
    @Modifying
    @Transactional
    int updateArticleById(String title, String keys, Category category,Long id);

//    Page<Article> findAll(Specification<Article> specification,Pageable pageable);
//    @Query("select a from  Atricle a where user_id = ?1")
//    List<Article> findByUserId(Long id);



}
