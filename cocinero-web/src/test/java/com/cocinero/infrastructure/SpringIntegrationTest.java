package com.cocinero.infrastructure;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static net.sourceforge.jwebunit.junit.JWebUnit.*;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = SpringIntegrationTest.TestConfig.class)
public abstract class SpringIntegrationTest {

    protected void register(String email,String password){
        beginAt("/index.html");
        clickLinkWithExactText("Sign Up");
        setTextField("email", email);
        setTextField("password", password);
        submit();
    }

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
    }
}
