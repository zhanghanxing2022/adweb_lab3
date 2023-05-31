package com.lab3.config;

import com.lab3.interceptor.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /* 拦截器配置 */
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(new UserInterceptor())
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login","/user/register");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /** 解决跨域问题 **/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .exposedHeaders(new String[]{"Authorization", "username"})
                .allowedMethods("*")
                .allowedHeaders("*").maxAge(3600);
        WebMvcConfigurer.super.addCorsMappings(registry);
    }
}
