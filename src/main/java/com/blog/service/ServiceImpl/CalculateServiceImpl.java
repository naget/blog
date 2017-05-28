package com.blog.service.ServiceImpl;

import com.blog.model.Article;
import com.blog.model.Review;
import com.blog.repository.ArticleRepository;
import com.blog.repository.ReviewRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;


/**
 * Created by t on 2017/3/15.
 */
@Service
public class CalculateServiceImpl {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    /**
     * 点开文章时，作者和文章的hotIndex加一
     *
     * @param articleId
     */
    public void hotService(Long articleId) {
        Article article = articleRepository.findById(articleId);
        article.setHotIndex(article.getHotIndex() + 1);
        article.setReadNumber(article.getReadNumber()+1);
        article.getUser().setHotIndex(article.getUser().getHotIndex() + 1);
        articleRepository.save(article);
    }

    public void likeHotService(Long articleId) {
        Article article = articleRepository.findById(articleId);
        article.setHotIndex(article.getHotIndex() + 1);
        article.setTopNumber(article.getTopNumber() + 1);
        article.getUser().setHotIndex(article.getUser().getHotIndex() + 1);
        articleRepository.save(article);
    }

    public void reviewArticleService(Long articleId,Long reviewerId,String content){
        Article article = articleRepository.findById(articleId);
        article.setHotIndex(article.getHotIndex() + 1);
        article.getUser().setHotIndex(article.getUser().getHotIndex() + 1);
        Review review=new Review(new Date(System.currentTimeMillis()),content,reviewerId);
        review.setArticle(article);
        reviewRepository.save(review);
    }
}
