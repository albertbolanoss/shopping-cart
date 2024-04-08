package com.ecommerce;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;


/**
 * @author : github.com/sharmasourabh
 * @project : Chapter06 - Modern API Development with Spring and Spring Boot Ed 2
 */
@Configuration
public class AppConfig {

    @Bean
    public ShallowEtagHeaderFilter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }
}