package com.blog.service.ServiceImpl;

import com.blog.model.Article;
import com.blog.model.Author;
import com.blog.model.User;
import com.blog.repository.ArticleRepository;
import com.blog.repository.ContentRepository;
import com.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wang0 on 2016/10/31 0031.
 */
@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ContentRepository contentRepository;
    private void formatGroup(List<User> userList){
        for (User user:userList){
            if (user.getGroups().equals("1"))user.setGroups("Web前端小组");
            if (user.getGroups().equals("2"))user.setGroups("嵌入式小组");
            if (user.getGroups().equals("3"))user.setGroups("Android移动开发小组");
            if (user.getGroups().equals("4"))user.setGroups("Web后端小组");
        }
    }
    public void formatContent(Article article){
        Long id=Long.valueOf(article.getContentId());//这有点问题，不要删掉valueOf
        article.setContent(contentRepository.findById(id).getContent());
    }
    public Page<User> getHottestUsers(){
        Sort sort=new Sort(Sort.Direction.DESC,"hotIndex");
        Page<User> userPage= userRepository.findAll(new PageRequest(0,3,sort));
        List<User> userList=userPage.getContent();
        formatGroup(userList);
        return userPage;
    }


    public Author findUserByArticleId(Long id) {
        Article article=articleRepository.findById(id);
        User user=userRepository.findById(article.getUser().getId());//通过article找到他的作者
        List<User> users=new ArrayList<User>();
        users.add(user);
        formatGroup(users);
        Integer articleNumber=articleRepository.findArticleCountByUserId(user.getId());
        Page<Article> latestArticles=articleRepository.findLatestArticles(user.getId(),new PageRequest(0,5));
        Author author=new Author(user.getId(),user.getNames(),user.getGroups(),user.getEmail(),user.getGrade(),articleNumber.toString(),user.getHotIndex(),latestArticles,user.getHead());

        return author;
    }

    /**
     * 通过UserId找到User,并返回一个Author
     * 其实不应该这样，包装成一个Author应该封装成一个函数
     * 此函数返回User比较好
     * @param id
     * @return
     */
    public Author findUserByUserId(Long id){
        User user=userRepository.findById(id);
        List<User> users=new ArrayList<User>();
        users.add(user);
        formatGroup(users);
        Integer articleNumber=articleRepository.findArticleCountByUserId(id);
        Page<Article> latestArticles=articleRepository.findLatestArticles(id,new PageRequest(0,5));
        Author author=new Author(user.getId(),user.getNames(),user.getGroups(),user.getEmail(),user.getGrade(),articleNumber.toString(),user.getHotIndex(),latestArticles,user.getHead());
        return author;
    }

}
