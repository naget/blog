package com.blog.controller;

import com.blog.model.*;
import com.blog.repository.*;
import com.blog.service.ArticleService;
import com.blog.service.ServiceImpl.AuthorServiceImpl;
import com.blog.service.ServiceImpl.CalculateServiceImpl;
import com.blog.service.ServiceImpl.UserServiceImpl;
import com.blog.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by t on 2017/3/28.
 */
@Controller
@RequestMapping("/author")
public class AuthorCtrl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorServiceImpl authorService;
    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ContentRepository contentRepository;
    @Autowired
    CalculateServiceImpl calculateService;
    @Autowired
    LikeRecordRepository likeRecordRepository;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CategoryRepository categoryRepository;
    private Long getUserId(){
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByNames(username).getId();
    }
    @RequestMapping(value = "/updateHead",method = RequestMethod.POST)
    @ResponseBody
    public Tdata<String, String, String> updateHead(@RequestParam("heading") MultipartFile file) {
//        Long userId = 95L;//得到用户ID
        String username= SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId=userRepository.findByNames(username).getId();
        return authorService.updateHead(file,userId);
    }

    @RequestMapping(value = "/postArticle",method = RequestMethod.POST)
    @ResponseBody
    public String postArticle(@RequestBody String all, HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf8");
        Map<String,String> map = JsonUtils.Split(all);
        String title = map.get("title");
        title = URLDecoder.decode(title, "utf8");
        String content= map.get("contentHtml");
        content = URLDecoder.decode(content, "utf8");
        String keys= map.get("keys");
        keys = URLDecoder.decode(keys, "utf8");
        Long categoryId=Long.valueOf(map.get("categoryId"));
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByNames(user).getId();
        return authorService.postArticle(title,content,keys,userId,categoryId);

    }
    @RequestMapping("/insertPicture")
    @ResponseBody
    public String insertPicture(@RequestParam(name = "myFileName") MultipartFile file){
        Long userId=getUserId();
        return authorService.insertPicture(file,userId);
    }
    @RequestMapping("/deleteArticle")
    @ResponseBody
    public String deleteArticle(Long articleId){
        return authorService.deleteArticle(articleId);
    }
    @RequestMapping("/editorArticle")
    public ModelAndView toEditorPage(Long articleId,ModelAndView modelAndView){
        Article article = articleService.getArticleDetail(articleId);
        modelAndView.addObject("article",article);
        modelAndView.setViewName("editor");
        return modelAndView;
    }

    @RequestMapping(value = "/reSaveArticle",method = RequestMethod.POST)
    @ResponseBody
    public String reSaveArticle(@RequestBody String all, HttpServletRequest request) throws  UnsupportedEncodingException{
        request.setCharacterEncoding("utf8");
        Map<String,String> map = JsonUtils.Split(all);
        String title = map.get("title");
        title = URLDecoder.decode(title, "utf8");
        String content= map.get("contentHtml");
        content = URLDecoder.decode(content, "utf8");
        String keys= map.get("keys");
        keys = URLDecoder.decode(keys, "utf8");
        Long categoryId=Long.valueOf(map.get("categoryId"));
        Long articleId = Long.valueOf(map.get("articleId"));
        Article article = articleService.getArticleDetail(articleId);
         Long contentId=article.getContentId();
         Category category = categoryRepository.findById(categoryId);
        if(articleRepository.updateArticleById(title,keys,category,articleId)>=1&&contentRepository.updateContent(content,contentId)>=-1)
        {
            return "更新成功";
        }
        else return "更新失败";

    }
    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    /**
     * 点赞
     * @param articleId
     * @return
     */
    @RequestMapping("/likeArticle")
    @ResponseBody
    public Tdata<String,Article,User> likeArticle(@RequestParam(value = "articleId") String articleId){

//        Long userId=95L;//得到此时用户id,防止重复点赞,用户没注册，不允许点赞
        String username=getCurrentUsername();
        Long userId = userRepository.findByNames(username).getId();
        List<LikeRecord> likeList= likeRecordRepository.findByLikerIdAndArticleId(userId,Long.valueOf(articleId));
        if (likeList.isEmpty()){
            calculateService.likeHotService(Long.valueOf(articleId));
            LikeRecord likeRecord=new LikeRecord(Long.valueOf(userId),Long.valueOf(articleId));
            likeRecordRepository.save(likeRecord);
            authorService.saveNotification(Long.valueOf(articleId),username,"赞");
            String message="成功点赞";
            return new Tdata<>(1,message);
        }else {
            String wrongMessage="不要重复点赞";
            return new Tdata<>(0,wrongMessage);
        }
    }

    /**
     * 评论文章
     * @param articleId
     * @param content
     */
    @RequestMapping("/reviewArticle")
    @ResponseBody
    public String reviewArticle(@RequestParam(value = "articleId") String articleId,@RequestParam(value = "content") String content){
//        Long reviewerId=95L;//得到此时用户ID，如果未登录，不准评论
        String username=getCurrentUsername();
        Long reviewerId = userRepository.findByNames(username).getId();
        authorService.saveNotification(Long.valueOf(articleId),username,"评论");
        calculateService.reviewArticleService(Long.valueOf(articleId), reviewerId,content);
        return "1";
    }
    @RequestMapping("/getNotification")
    @ResponseBody
    public Page<Notification> getNotification(){
        Long authorId=userRepository.findByNames(getCurrentUsername()).getId();
        return authorService.getNotification(getCurrentUsername(),authorId,new PageRequest(0,10));
    }

    @RequestMapping("/reEditor/{articleId}")
    public ModelAndView reEditor(@PathVariable String articleId,ModelAndView modelAndView){
        Article article = articleService.getArticleDetail(Long.valueOf(articleId));
        String keys = article.getContent();
        Long categoryId=article.getCategory().getId();
        userService.formatContent(article);
        modelAndView.addObject("title",article.getTitle());
        modelAndView.addObject("articleContent",article.getContent());
        modelAndView.addObject("keys",keys);
        modelAndView.addObject("categoryId",categoryId);
        modelAndView.addObject("articleId",articleId);
        modelAndView.setViewName("reEditor");
        return modelAndView;
    }



}
