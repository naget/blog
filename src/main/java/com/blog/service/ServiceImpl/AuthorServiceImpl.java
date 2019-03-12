package com.blog.service.ServiceImpl;

import com.blog.model.*;
import com.blog.repository.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 作者个人主页
 * Created by t on 2017/3/29.
 */
@Service
public class AuthorServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ContentRepository contentRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Value("${web.upload-path}")//读取配置信息(头像路径)
    private String headPath;
//    private String savePathHead="/apache-tomcat-7.0.72/apache-tomcat-7.0.72/webapps";//本机测试路径
    private String savePathHead="/home/apache-tomcat-7.0.70/webapps";//服务器路径

    /**
     * 判断是否为图片文件
     * @param fileName
     * @return
     */
    private Boolean isImageFile(String fileName) {
        String [] img_type = new String[]{".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if(fileName==null) {return false;}
        fileName = fileName.toLowerCase();//变小写
        for(String type : img_type) {
            if(fileName.endsWith(type)) {return true;}
        }
        return false;
    }
    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    private String getFileType(String fileName) {
        if(fileName!=null && fileName.indexOf(".")>=0) {
            return fileName.substring(fileName.lastIndexOf("."), fileName.length());
        }
        return "";
    }
    public Tdata<String,String,String> updateHead(MultipartFile file,Long userId){

        if (file!=null){
            BufferedOutputStream bw=null;
            try {
                String fileName=file.getOriginalFilename();
                if (fileName!=null&&!"".equalsIgnoreCase(fileName.trim())&&isImageFile(fileName)){
                    String pathname=headPath+userId+"/"+"heading"+getFileType(fileName);
                    File outFile = new File(savePathHead+pathname);
                    FileUtils.copyInputStreamToFile(file.getInputStream(),outFile);
                    User user=userRepository.findById(userId);
                    if (user!=null){
                        user.setHead("http://blog.swpuiot.com"+pathname);
                        userRepository.save(user);
                    }else return new Tdata<>(0,"用户为空");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw!=null){
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new Tdata<>(1,"上传成功");
        }
        return new Tdata<>(0,"上传失败");


    }
    /**
     * 上传文章图片
     */
    public String insertPicture(MultipartFile file,Long userId){
        if (file!=null){
            BufferedOutputStream bw=null;
            try {
                String fileName=file.getOriginalFilename();
                if (fileName!=null&&!"".equalsIgnoreCase(fileName.trim())&&isImageFile(fileName)){
                    String pathname="/articlePicture/"+userId+"/"+Math.random()*1000000000+getFileType(fileName);
                    File outFile = new File(savePathHead+pathname);
                    FileUtils.copyInputStreamToFile(file.getInputStream(),outFile);
                    return "http://blog.swpuiot.com"+pathname;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw!=null){
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
       return "error|nullFile";


    }
    /**
     * post文章
     */
    public String postArticle(String title,String content,String keys,Long UserId,Long categoryId){
        User user = userRepository.findById(UserId);
        Category category = null;
        if (categoryId.equals(Long.valueOf("0"))){
            category =categoryRepository.findById(Long.valueOf(user.getGroups()));
        }else {
            category = categoryRepository.findById(categoryId);
        }

        Article article = new Article(null,title,keys,new Timestamp(System.currentTimeMillis()),0,0,0,0,0,user,category);
        Long maxId=contentRepository.findMaxId().get(0)==null?-1:contentRepository.findMaxId().get(0).getId();
        contentRepository.save(new ArticleContent(content));
        compareAndSet(maxId,article,content);
        return "发表成功";

    }
    private void compareAndSet(Long expect,Article article,String content){
        Long maxId=contentRepository.findMaxId().get(0).getId();
        if (maxId==expect+1){
            article.setContentId(maxId);
            articleRepository.save(article);
        }else {
            Long realId=contentRepository.findByContent(content).getId();
            article.setContentId(realId);
            articleRepository.save(article);
        }
    }

    /**
     * 删除文章
     * @param articleId
     * @return
     */
    public String deleteArticle(Long articleId){
        Article article=articleRepository.findById(articleId);
        boolean flag= article != null;
        while (flag){
            Long contentId=article.getContentId();
            articleRepository.deleteById(articleId);
            contentRepository.deleteById(contentId);
            reviewRepository.deleteByArticleId(articleId);
            flag = false;
            return "成功删除";
        }
       return "文章不存在";
    }
    public void saveNotification(Long articleId,String actioner,String actionName){
        Long authorId=articleRepository.findById(articleId).getUser().getId();
        notificationRepository.save(new Notification(authorId,actioner,actionName,articleId));
    }
    public Page<Notification> getNotification(String authorName,Long autherId, Pageable pageable){
         Page<Notification> page= notificationRepository.findByAuthorId(autherId,pageable);
         formatNotification(page,authorName);
         return page;
    }
    private void formatNotification(Page<Notification> notifications,String authorName){
        List<Notification> notificationList = notifications.getContent();
        for (Notification notification:notificationList){
            notification.setAuthorName(authorName);
        }
    }


}
