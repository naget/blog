package com.blog.service.ServiceImpl;

import com.blog.model.Article;
import com.blog.repository.ArticleRepository;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by t on 2016/11/4.
 */

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    @Override
    public Page<Article> getLatestArticles( Pageable pageable) {


        return articleRepository.findAll(pageable);
    }
    @Override
    public Page<Article> getSpecialArticles(Long category_id,Pageable pageable)
    {

        return articleRepository.findByCategoryId(category_id,pageable);
    }

    @Override
    public Page<Article> getHottestArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Page<Article> getPersonalArticles(String s, Pageable pageable) {
        return articleRepository.findPersonally(s,pageable);
    }

//    @Override
//    public Page<Article> getHottestArticle(@PageableDefault(value = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable) {
//        Page<Article> articlePage=articleRepository.findAll(new Specification<Article>(){
//            @Override
//            public Predicate toPredicate(Root<Article> root , CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder
//            ){
//                root=query.from(Article.class);
//                Path<Integer> categoryExp=root.get("category_id");
//                query.where(criteriaBuilder.equal(root.getModel().));
//                return null;
//            }
//        },new PageRequest(1,1,new Sort(Sort.Direction.DESC,new String[]{"id"})));
//        StringBuilder stout=new StringBuilder("以下是类别是1的文章信息：").append("\n");
//        stout.append("|序号|标题|");
//        int sortIndex=1;
//        for (Article a:articlePage.getContent()){
//            stout.append("|").append(sortIndex);
//            stout.append("|").append(a.getTitle());
//            stout.append("|").append(a.getContent());
//            sortIndex++;
//        }
//        System.err.println(stout);
//
//    }


}
