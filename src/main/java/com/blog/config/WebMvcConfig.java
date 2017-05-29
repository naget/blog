package com.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {

		registry.addViewController("/index").setViewName("index-blog");
		registry.addViewController("/COA").setViewName("COA");
		registry.addViewController("/COB").setViewName("COB");
		registry.addViewController("/COE").setViewName("COE");
		registry.addViewController("/COF").setViewName("COF");
		registry.addViewController("/toAuthor").setViewName("blog-author");
		registry.addViewController("/textHead").setViewName("updateHeadingTest");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/editor").setViewName("editor");
		registry.addViewController("/toPersonalInfo").setViewName("person-info");
	}


}
