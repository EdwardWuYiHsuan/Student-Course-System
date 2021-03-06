package com.poseitech.assignment.view;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class IndexController extends WebMvcConfigurerAdapter {

	@Override  
    public void addViewControllers(ViewControllerRegistry registry) 
	{  
        registry.addViewController("/").setViewName("index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);  
    }  

	@Override  
    public void configurePathMatch(PathMatchConfigurer configurer) 
	{  
        super.configurePathMatch(configurer);  
        configurer.setUseSuffixPatternMatch(false);  
    }
	
}
