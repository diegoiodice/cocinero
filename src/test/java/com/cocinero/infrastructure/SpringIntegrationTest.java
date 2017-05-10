package com.cocinero.infrastructure;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataMongoTest
@ContextConfiguration(classes = SpringIntegrationTest.TestConfig.class)
public abstract class SpringIntegrationTest {

    /*private TestContextManager contextManager;

    public SpringIntegrationTest(){
        contextManager = new TestContextManager(this.getClass());
        try {
            contextManager.prepareTestInstance(this);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(),e);
        }
    }

    @After
    public void tearDown() throws Exception {

        contextManager.afterTestMethod(this, this.getClass().getMethods()[0],new RuntimeException("tearDownException"));
    }*/

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
