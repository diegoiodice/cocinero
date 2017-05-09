package com.cocinero.infrastructure.web.message;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.RoutingContext;
import lombok.Builder;
import org.apache.commons.lang3.StringUtils;

import java.util.Base64;

@Builder(buildMethodName = "create")
public class FlashHandler implements Handler<RoutingContext> {

    @Override
    public void handle(RoutingContext ctx) {

        Cookie flashCookie = ctx.getCookie("flash_message");
        if(flashCookie != null){
            byte[] cookieValue = Base64.getDecoder().decode(flashCookie.getValue());
            ctx.put("flash_message",new FlashMessage(cookieValue));
            ctx.addCookie(Cookie.cookie("flash_message","").setMaxAge(0));
        }
        ctx.data().computeIfAbsent("flash_message", k -> new FlashMessage());
        ctx.next();
    }

    private static void put(RoutingContext ctx,String key, String value){
        FlashMessage flashMessage = ctx.get("flash_message");
        flashMessage.put(key,value);
        String encodedCookie = Base64.getEncoder().encodeToString(flashMessage.getFlash().encode().getBytes());
        ctx.addCookie(Cookie.cookie("flash_message", encodedCookie).setPath("/"));
    }

    public static void addError(RoutingContext ctx, String value){
        FlashHandler.put(ctx,"error",value);
    }

    public static void addSuccess(RoutingContext ctx, String value){
        FlashHandler.put(ctx,"success",value);
    }

    private class FlashMessage {

        private final JsonObject flash;

        public JsonObject getFlash() {
            return flash;
        }

        public FlashMessage() {
            flash = new JsonObject();
        }

        public FlashMessage(byte[] flashContent){
            String json = new String(flashContent);
            if (StringUtils.isNotBlank(json)) {
                this.flash = new JsonObject(json);
            }else{
                this.flash = new JsonObject();
            }
        }

        public void put(String key, String value){
            flash.put(key,value);
        }

        public String getString(String key){
            return flash.getString(key);
        }
    }
}
