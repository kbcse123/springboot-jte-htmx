package com.api.performance.config;

import gg.jte.TemplateEngine;
import gg.jte.resolve.ResourceCodeResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

//@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public TemplateEngine templateEngine() {
        // For development, use the directory-based template resolver
        // In production, you might want to use a different configuration
        String templateDirectory = Paths.get("src", "main", "resources", "templates").toAbsolutePath().toString();
        return TemplateEngine.create(
            new ResourceCodeResolver(templateDirectory), 
            gg.jte.ContentType.Html
        );
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Redirect root to the benchmark form
        registry.addViewController("/").setViewName("redirect:/benchmark");
    }
}
