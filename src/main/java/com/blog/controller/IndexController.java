package com.blog.controller;

import com.blog.model.*;
import com.blog.repository.LikeRecordRepository;
import com.blog.repository.UserRepository;
import com.blog.service.ArticleService;
import com.blog.service.ServiceImpl.CalculateServiceImpl;
import com.blog.service.ServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

/**
 * Created by t on 2017/3/5.
 */
@Controller
public class IndexController {
    @Autowired
    ArticleService articleService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CalculateServiceImpl calculateService;
    @Autowired
    LikeRecordRepository likeRecordRepository;
    @Autowired
    UserRepository userRepository;
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    @RequestMapping("/firstShow")
    @ResponseBody
    public Page<Article> firstShow(@RequestParam(value = "page",defaultValue = "0")Integer page,
                                   @RequestParam(value = "size",defaultValue = "10")Integer size,
                                   @RequestParam(value = "index",defaultValue = "")String index){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        if (index!=null&&!index.equals(""))
        {
            System.out.println("模糊查询");
            return articleService.getPersonalArticles(index,new PageRequest(page,size));
        } else
        {
            System.out.println("最新查询");
            return articleService.getLatestArticles(new PageRequest(page,size,sort));
        }
    }
    @RequestMapping("/getHottestArticles")
    @ResponseBody
    public Page<Article> getHottestArticles(){
        Sort sort=new Sort(Sort.Direction.DESC,"hotIndex");
        return articleService.getHottestArticles(new PageRequest(0,10,sort));
    }
    @RequestMapping("/getHottestUsers")
    @ResponseBody
    public Page<User> getHottestUsers(){
        return userService.getHottestUsers();
    }
    @RequestMapping("/getSpecialArticles")
    @ResponseBody
    public Page<Article> getSpecialArticles(@RequestParam(value = "id")Long id,
                                            @RequestParam(value = "page",defaultValue = "0")Integer page,
                                            @RequestParam(value = "size",defaultValue = "10")Integer size){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        return articleService.getSpecialArticles(id,new PageRequest(page,size));

    }
    @RequestMapping("/getPageChangePArticle")
    @ResponseBody
    public Page<Article> getPageChangePArticle(@RequestParam(value = "id")Long id,
                                               @RequestParam(value = "page",defaultValue = "0")Integer page,
                                               @RequestParam(value = "size",defaultValue = "10")Integer size){
        Sort sort =new Sort(Sort.Direction.DESC,"id");
        return articleService.getArticlesByAuthorId(id,new PageRequest(page,size));

    }

    /**
     * 点开文章的瞬间，变换页面，将文章id存入session，配合下边两个方法（感觉这样不太好，但我还是不想改了。。。）
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/article/{id}")
    public String toArticle(@PathVariable(value = "id") String id, HttpServletRequest request){
        System.out.println("文章id是："+id);
        HttpSession session=request.getSession();
        session.setAttribute("articleId",id);
        calculateService.hotService(Long.valueOf(id));
        return "blog-article";
    }

    /**
     * 功能：进入文章查看页之后，得到文章，作者详细信息
     * getArticleDetail这个方法没写好，耦合度有点大。
     * 搞得分页时的跳转函数不好写了，但我也不打算改了。。。
     * 下边我再加个方法，下不为例！
     * @param request
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/getArticleDetail")
    @ResponseBody
    public Tdata<Article,Page<Review>,Author> getArticleDetail(HttpServletRequest request,
                                                  @RequestParam(value = "page",defaultValue = "0")Integer page,
                                                  @RequestParam(value = "size",defaultValue = "3")Integer size){
        HttpSession session=request.getSession();
        Long id=Long.valueOf(session.getAttribute("articleId").toString());
        Article article=articleService.getArticleDetail(id);
        userService.formatContent(article);
        Author author =userService.findUserByArticleId(id);
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        Page<Review> reviewPage=articleService.getReviewDetail(id,new PageRequest(page,size,sort));
        return new Tdata<>(200,article,reviewPage,author);
    }

    /**
     * 文章详情页的评论页面跳转
     * @param page
     * @param size
     * @param articleId
     * @return
     */
    @RequestMapping("/reviewPageChange")
    @ResponseBody
    public Page<Review> reviewsPageChange(@RequestParam(value = "page")String page,
                                          @RequestParam(value = "size")String size,
                                          @RequestParam(value = "articleId")String articleId){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        return articleService.getReviewDetail(Long.valueOf(articleId),new PageRequest(Integer.valueOf(page),Integer.valueOf(size),sort));
    }



    /**
     * 通过作者id得到作者详细信息
     * @param authorId
     * @return
     */
    @RequestMapping("/getAuthorDetail")
    @ResponseBody
    public Author getAuthorDetailByUserId(@RequestParam(value = "authorId") String authorId){


        return userService.findUserByUserId(Long.valueOf(authorId));
    }


    @RequestMapping("/getAuthor/{id}")
    public ModelAndView toAuthor(@PathVariable(value = "id") Long id,ModelAndView model){

        model.addObject("id",id);
        model.setViewName("blog-author");

        return model;

    }
    @RequestMapping("/getAuthorHead/{path}")
    @ResponseBody
    public File getPicture(@PathVariable(value = "path")String path){
        File file=new File(path+"?"+Math.random());
        return file;
    }
    @RequestMapping("/getPersonalInfo")
    public ModelAndView toPersonalInfo(ModelAndView model){
        Long id = userRepository.findByNames(getCurrentUsername()).getId();
        System.out.println("_____________id是____________________"+id);
        model.addObject("id",id);
        model.setViewName("person-info");

        return model;
    }
}
