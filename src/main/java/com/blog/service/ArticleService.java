package com.blog.service;

import com.blog.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by t on 2016/11/4.
 */

@Service
public interface ArticleService {
    public Page<Article> getLatestArticle(Pageable pageable);



}
