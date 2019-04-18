package com.social.portals.configuration;


import com.social.portals.Interceptor.LogInterceptor;
import com.social.portals.Interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfiguration implements WebMvcConfigurer {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    LogInterceptor logInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(logInterceptor).addPathPatterns("/ask/**", "/follow/**", "/letter/**");
    }
}
