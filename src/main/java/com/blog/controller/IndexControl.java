package com.blog.controller;

import com.blog.model.Article;
import com.blog.repository.ArticleRepository;
import com.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    @RequestMapping("/showLatestArticles")
    @ResponseBody
    public Page<Article> showLatestArticles(@PageableDefault(value = 10,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,HttpServletResponse response)
    {
        Page<Article> articles = articleService.getLatestArticles(pageable);
        return  articles;
    }
    @RequestMapping("/showSpecialTypeArticles/{type}")
    @ResponseBody
    public Page<Article> showSpecialArticles(@PathVariable("type") Long type)
    {
        Page<Article> articles=articleService.getSpecialArticles(type,new PageRequest(0,3));
        return articles;
    }
    @RequestMapping("/showHottestArticles")
    @ResponseBody
    public Page<Article> showHottestArticles(@PageableDefault(value = 10,sort = {"hotIndex"},direction = Sort.Direction.DESC)Pageable pageable)
    {
        Page<Article> articles=articleService.getHottestArticles(pageable);
        return articles;
    }
    @RequestMapping("/showPersonalArticles/{index}")
    @ResponseBody
    public Page<Article> showPersonalArticles(@PathVariable("index")String index,@PageableDefault(value = 10,sort = {"hotIndex"},direction = Sort.Direction.ASC)Pageable pageable)
    {
        Page<Article> articles=articleService.getPersonalArticles(index,pageable);
        return articles;
    }



}
