package com.cocinero.infrastructure;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = SpringIntegrationTest.TestConfig.class)
public abstract class SpringIntegrationTest {

    @Configuration
    @ComponentScan("com.cocinero.infrastructure")
    public static class TestConfig extends AbstractMongoConfiguration {

        @Override
        protected String getDatabaseName() {
            return "cocinerodb-test";
        }

        @Override
        public Mongo mongo() throws Exception {
            return new Fongo(getDatabaseName()).getMongo();
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
        public ObjectMapper objectMapper(){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper;
        }
    }
}
