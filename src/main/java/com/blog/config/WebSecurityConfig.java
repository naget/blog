package com.blog.config;

import com.blog.service.ServiceImpl.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by t on 2016/11/5.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService(){return new UserService();}
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserService());
    }
    protected void configure(HttpSecurity http) throws Exception{
          http.authorizeRequests()
                  .antMatchers("/getPageChangePArticle","/firstShow","/**/*.html","/**/*.js","/**/*.css","/**/*.jpg","/**/*.jpeg","/**/*.png","/*.jpg","/index","/getHottestArticles","/getHottestUsers","/article/**","/getArticleDetail","/getAuthorDetail","/getAuthor/**","/COA","/COB","/COE","/COF","/getSpecialArticles").permitAll()
                  .anyRequest().authenticated()
                  .and().formLogin().loginPage("/login").defaultSuccessUrl("/index",true)
                  .failureUrl("/error").permitAll();

          http.csrf().disable();
    }

}
