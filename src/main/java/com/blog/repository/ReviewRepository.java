package com.blog.repository;

import com.blog.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by t on 2016/10/31.
 */
public interface ReviewRepository extends JpaRepository<Review,Long>{
    List<Review> findByArticleId(Long id);
    Page<Review> findByArticleId(Long id, Pageable pageable);
    List<Review> findById(Long id);
    @Modifying
    @Transactional
    @Query("DELETE from Review r where article_id=?1")
    void deleteByArticleId(Long id);
}
