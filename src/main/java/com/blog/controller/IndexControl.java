package com.blog.controller;

import com.blog.model.Article;
import com.blog.repository.ArticleRepository;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by wang0 on 2016/10/31 0031.
 */

@Controller
public class IndexControl {
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
//    @RequestMapping("/")
////    @ResponseBody
//    public ModelAndView showLatestArticle(HttpServletRequest request, HttpServletResponse response) throws Exception{
//        ModelAndView modelAndView=new ModelAndView("/index");
//        Page<Article> articlePage= articleService.getLatestArticle(new PageRequest(1,10));
//        modelAndView.addObject("info",articlePage.getContent().get(2));
//        modelAndView.addObject("totalPageNumber",articlePage.getTotalElements());
//        modelAndView.addObject("pageSize",articlePage.getTotalPages());
//        return modelAndView;
//    }
    @RequestMapping("/")
    @ResponseBody
    public Page<Article> showLatestArticle(HttpServletResponse response)
    {
        Page<Article> articles = articleService.getLatestArticle(new PageRequest(1,1));
        return  articles;

    }


}
