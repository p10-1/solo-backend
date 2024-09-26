package org.solo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
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
        "org.solo.asset.controller",
        "org.solo.policy.controller",
        "org.solo.policy.scheduler",
        "org.solo.product.controller"

})  // <- 공통 팩키지
public class ServletConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/resources/index.html");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

        registry.addResourceHandler("/assets/**")
                .addResourceLocations("/resources/assets/");

        registry.addResourceHandler("/img/**")
                .addResourceLocations("/img/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173") // 프론트엔드 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true);
    }

    //	Servlet 3.0 파일 업로드 사용시
    @Bean
    public MultipartResolver multipartResolver() {
        StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
        return resolver;
    }
}













