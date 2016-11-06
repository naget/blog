package com.blog.service.ServiceImpl;

import com.blog.model.Article;
import com.blog.repository.ArticleRepository;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

/**
 * Created by t on 2016/11/4.
 */

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Page<Article> getLatestArticle(@PageableDefault(value = 10,sort = {"pubtime"},direction = Sort.Direction.DESC)Pageable pageable) {

        return articleRepository.findAll(pageable);
    }
}
