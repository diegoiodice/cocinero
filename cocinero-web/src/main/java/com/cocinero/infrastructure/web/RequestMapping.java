package com.cocinero.infrastructure.web;

import io.vertx.core.http.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    HttpMethod method() default HttpMethod.GET;

    String path();

    String failureHandler() default "defaultFailureHandler";
}
