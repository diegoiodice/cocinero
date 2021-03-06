package com.cocinero.infrastructure.web.controller;

import com.cocinero.domain.Address;
import com.cocinero.domain.User;
import com.cocinero.infrastructure.web.RequestMapping;
import com.cocinero.repository.AddressRepository;
import com.cocinero.repository.UserRepository;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequestMapping(path="/users")
public class UserController extends AbstractController{

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path="/:id/addresses/new")
    public Handler<RoutingContext> newAddressView(){
        return ctx->ctx.put("view","addresses/new").next();
    }

    @RequestMapping(path="/:id/addresses",method = HttpMethod.POST)
    public Handler<RoutingContext> addAddress(){

        return ctx->{

            String postCode = ctx.request().getParam("postCode");
            String addressLine1 = ctx.request().getParam("addressLine1");
            String addressLine2 = ctx.request().getParam("addressLine2");
            String city = ctx.request().getParam("city");
            String country = ctx.request().getParam("country");

            Address address = Address.builder()
                    .addressLine1(addressLine1)
                    .addressLine2(addressLine2)
                    .city(city)
                    .postCode(postCode)
                    .country(country).build();

            Address addressWithId = addressRepository.create(address);
            User currentUser = userRepository.findById(ctx.pathParam("id"));
            //currentUser.getAddresses().add(addressWithId);
            userRepository.save(currentUser);

            doRedirect(ctx,"/addresses/show/"+addressWithId.getId());
        };
    }
}
