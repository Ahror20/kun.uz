package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.servlet.Filter;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private TokenFilter tokenFilter;
    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean() {
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<>();
        bean.setFilter(tokenFilter);
        bean.addUrlPatterns("/region/adm");
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/region/adm/**");
        bean.addUrlPatterns("/category/adm/");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/category/adm/**");
        bean.addUrlPatterns("/profile/adm");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/profile/adm/**");
        bean.addUrlPatterns("/email_send_history/adm/*");
        bean.addUrlPatterns("/sms_history/adm/*");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/article/moderator");
        bean.addUrlPatterns("/article/moderator/*");
        bean.addUrlPatterns("/article-type/publisher");


        return bean;
    }


}
