package com.cocinero.infrastructure.web.controller;


import com.cocinero.domain.User;
import com.cocinero.domain.UserProfile;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.repository.UserProfileRepository;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static java.util.Optional.ofNullable;

@Component
@RequestMapping(path = "/userProfiles")
public class UserProfileController extends AbstractController{

    private DateTimeFormatter df = DateTimeFormatter.ISO_DATE;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @RequestMapping(path="/:id/edit")
    public Handler<RoutingContext> editUserProfileView(){
        return ctx->{
            User user = ctx.get("user");
            UserProfile userProfile = ofNullable(userProfileRepository.findById(user.getId()))
                    .orElse(UserProfile.builder().build());
            ctx.put("userProfile",userProfile);
            ctx.put("view","users/profile/edit").next();
        };
    }

    @RequestMapping(path="/",method = HttpMethod.PUT)
    public Handler<RoutingContext> editUserProfile(){
        return ctx->{
            User user = ctx.get("user");
            HttpServerRequest request = ctx.request();
            userProfileRepository.save(UserProfile.builder()
                    .id(user.getId())
                    .names(request.getFormAttribute("names"))
                    .lastNames(request.getFormAttribute("lastNames"))
                    .dateOfBirth(getDateOfBirth(request.getFormAttribute("dateOfBirth")))
                    .telephone(request.getFormAttribute("telephone"))
                    .build());

            ctx.put("view","events/index").next();
        };
    }

    private Date getDateOfBirth(String dateOfBirth) {
        return Date.from(LocalDate.parse(dateOfBirth).atStartOfDay().toInstant(ZoneOffset.UTC));
    }
}
