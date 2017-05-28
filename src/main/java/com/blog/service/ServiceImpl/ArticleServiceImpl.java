package com.blog.service.ServiceImpl;

import com.blog.model.Article;
import com.blog.model.Review;
import com.blog.repository.ArticleRepository;
import com.blog.repository.ReviewRepository;
import com.blog.repository.UserRepository;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by t on 2016/11/4.
 */

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserServiceImpl userService;
    @Override
    public Page<Article> getLatestArticles(Pageable pageable) {


        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> getSpecialArticles(Long category_id, Pageable pageable) {

        return articleRepository.findByCategoryId(category_id, pageable);
    }

    @Override
    public Page<Article> getHottestArticles(Pageable pageable) {

        Page<Article> articles0 = articleRepository.findAll(pageable);
        List<Article> articleList=articles0.getContent();
        for (Article article:articleList)
        {
            article.setAuthor(userRepository.findById(article.getUser().getId()).getNames());
        }

        return articles0;
    }

    @Override
    public Page<Article> getPersonalArticles(String s, Pageable pageable) {
        return articleRepository.findPersonally(s, pageable);
    }
    @Override
    public Page<Article> getArticlesByAuthorId(Long id,Pageable pageable){
        Page<Article> articlePage= articleRepository.findArticlesByAuthorId(id,pageable);
        return articlePage;
    }
//    @Override
//    public Page<Article> getHottestArticle(Specification<Article> specification, Pageable pageable) {
//        return null;
//    }

    @Override
    public Page<Article> getHottestArticle(final int type, Pageable pageable) {
        Page<Article> articlePage = articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder
            ) {

//                Predicate p1 = criteriaBuilder.equal(root.get("id").as(int.class),type);
                root = query.from(Article.class);
                Path<Integer> categoryExp = root.get("id");
                query.where(criteriaBuilder.equal(categoryExp,type));


                return query.getRestriction();

            }
        }, new PageRequest(0, 4, new Sort(Sort.Direction.DESC, new String[]{"id"})));
//        StringBuilder stout = new StringBuilder("以下是类别是1的文章信息：").append("\n");
//        stout.append("|序号|标题|");
//        int sortIndex = 1;
//        for (Article a : articlePage.getContent()) {
//            stout.append("|").append(sortIndex);
//            stout.append("|").append(a.getTitle());
//            stout.append("|").append(a.getContent());
//            sortIndex++;
//        }
//        System.err.println(stout);
        return articlePage;

    }

    @Override
    public Article getArticleDetail(Long articleId){
        Article article=articleRepository.findById(articleId);

        return article;

    }

    @Override
    public Page<Review> getReviewDetail(Long articleId,Pageable pageable){
        Page<Review> reviewPage=reviewRepository.findByArticleId(articleId,pageable);
        List<Review> reviewList=reviewPage.getContent();
        addReview(reviewList);
        return reviewPage;
    }



    private void addReview(List<Review> reviewList){
        for (Review review :
                reviewList) {
            review.setReviewerName(userRepository.findById(review.getReviewerId()).getNames());
            if (null!=review.getHasChild()) {
                review.setChildList(reviewRepository.findById(review.getChildId()));
                addReview(review.getChildList());
            }
        }
    }



}
