package org.solo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
//@ComponentScan(basePackages = "org.solo")  // controller
@ComponentScan(basePackages = {
        "org.solo.controller",
        "org.solo.asset.controller",
        "org.solo.board.controller",
        "org.solo.member.controller",
        "org.solo.news.controller",
        "org.solo.policy.controller",
        "org.solo.exception"
})
public class ServletConfig implements WebMvcConfigurer {

    @Bean   // <- object 생성
    public InternalResourceViewResolver viewResolver() {
        System.out.println("WebConfig viewResolver() ~~~");

        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
















