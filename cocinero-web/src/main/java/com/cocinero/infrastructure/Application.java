package com.cocinero.infrastructure;

import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Application {

	@Autowired
	private Server server;

	public static void main(String[] args) {
        String activeProfile = System.getProperty("spring.profiles.active","");
        System.setProperty("vertx.disableFileCaching", Boolean.toString(activeProfile.contains("dev")));
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void deployVerticle() {
		Vertx.vertx().deployVerticle(server);
	}
}
