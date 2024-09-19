package org.solo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
        "org.solo.controller",
        "org.solo.exception",
        "org.solo.member.controller",
        "org.solo.board.controller",
        "org.solo.news.controller",
        "org.solo.mypage.controller",
        "org.solo.policy.controller",
        "org.solo.asset.controller"
})  // <- 공통 팩키지
public class ServletConfig implements WebMvcConfigurer {

    @Bean   // <- object 생성
    public InternalResourceViewResolver viewResolver() {
        System.out.println("WebConfig viewResolver() ~~~");
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/");
    }
}
















