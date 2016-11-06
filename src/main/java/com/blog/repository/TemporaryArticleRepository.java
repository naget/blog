package com.blog.repository;

import com.blog.model.TemporaryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by t on 2016/10/31.
 */
public interface TemporaryArticleRepository extends JpaRepository<TemporaryArticle,Long>{
}
