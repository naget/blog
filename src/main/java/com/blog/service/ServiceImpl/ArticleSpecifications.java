//package com.blog.service.ServiceImpl;
//
//import com.blog.model.Article;
//import org.springframework.data.jpa.domain.Specification;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//
///**
// * Created by t on 2016/11/6.
// */
//public class ArticleSpecifications{
//    public static Specification<Article> isSpecialType(){
//        return new Specification<Article>() {
//            @Override
//            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//                return cb.equal(root.get(Article.categoryId),2);
//            }
//        };
//
//    }
//
//}