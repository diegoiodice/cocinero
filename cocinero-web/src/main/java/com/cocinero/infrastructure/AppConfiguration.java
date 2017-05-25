package com.cocinero.infrastructure;

import com.cocinero.repository.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.ext.web.templ.TemplateEngine;
import io.vertx.ext.web.templ.ThymeleafTemplateEngine;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class AppConfiguration {

    @Autowired
    Environment environment;

    @Autowired
    private UserRepository userRepository;

    int httpPort() {
        return environment.getProperty("http.port", Integer.class, 9000);
    }

    public String getWebRoot() {
        return "webroot/delicious";
    }

    @Bean
    public ValidatingMongoEventListener validatingMongoEventListener() {
        return new ValidatingMongoEventListener(validator());
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public TemplateEngine templateEngine(){
        ThymeleafTemplateEngine engine = ThymeleafTemplateEngine.create();
        engine.getThymeleafTemplateEngine().addDialect(new LayoutDialect());
        return engine;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

    @Bean
    public DateFormat dateFormatter(){
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}