package com.cocinero.infrastructure.web.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.util.*;

@Slf4j
@Builder(buildMethodName = "create")
public class FlashHandler implements Handler<RoutingContext> {

    private final Environment environment;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(RoutingContext ctx) {

        Cookie flashCookie = ctx.getCookie("flash_message");
        if(flashCookie != null){
            try {
                ctx.put("flash_message",
                        objectMapper.readValue(Base64.getDecoder().decode(flashCookie.getValue()), Map.class));
            } catch (IOException e) {
                log.error(e.getMessage(),e);
            }
        }else{
            Map<String,Map<String,String>> messages = new HashMap<>();
            messages.put("errors",new HashMap<>());
            messages.put("success",new HashMap<>());
            ctx.put("flash_message",messages);
        }
        ctx.next();
    }

    public void addError(RoutingContext ctx, String key, String value){
        Map<String,Map<String,String>> messages = ctx.get("flash_message");
        messages.get("errors").put(key,environment.getProperty(value,value));
    }

    public void addSuccess(RoutingContext ctx, String key, String value){
        Map<String,Map<String,String>> messages = ctx.get("flash_message");
        messages.get("success").put(key,environment.getProperty(value,value));
    }

    public void persistMessages(RoutingContext ctx){
        Map<String,List<JsonObject>> messages = ctx.get("flash_message");
        try {
            ctx.addCookie(Cookie.cookie("flash_message",
                    Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(messages)))
                    .setMaxAge(5).setPath("/"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
