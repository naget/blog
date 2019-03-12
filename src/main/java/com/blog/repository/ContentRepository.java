package com.blog.repository;

import com.blog.model.ArticleContent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by t on 2017/4/17.
 */
@Repository
public interface ContentRepository extends JpaRepository<ArticleContent,Long> {
    @Cacheable(value = "thisredis",keyGenerator = "firstParamKeyGenerator")
    ArticleContent findById(Long id);
    @Query("SELECT c from ArticleContent c  order by c.id desc")
    List<ArticleContent> findMaxId();
    ArticleContent findByContent(String content);
    @Modifying
    @Transactional
    @Query("DELETE from ArticleContent c where c.id=?1")
    void deleteById(Long id);
    @Query("update ArticleContent c set c.content=?1 where c.id=?2")
    @Modifying
    @Transactional
    int updateContent(String content,Long id);
}
