package com.RockCafe.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer 
{
    @Override
    public void addViewControllers(ViewControllerRegistry registry) 
    {
        registry.addRedirectViewController("/", "/api/list_items");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/images/**") // CORREÇÃO: Use ** para pegar qualquer arquivo
            .addResourceLocations("file:./src/main/resources/static/images/");
    }
}