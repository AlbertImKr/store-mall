package com.albert.commerce.common.units;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.albert.commerce.store.ui.ConsumerStoreController;
import com.albert.commerce.store.ui.SellerStoreController;
import com.albert.commerce.user.ui.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class BusinessLinks {

    private BusinessLinks() {
    }

    public static final Link MY_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SellerStoreController.class)
                            .getMyStore(null))
                    .withRel("my-store");
    public static final Link CREATE_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SellerStoreController.class)
                            .createStore(null, null, null))
                    .withRel("create-store");
    public static final Link GET_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ConsumerStoreController.class)
                            .getStore(null))
                    .withRel("get-store");

    public static final Link GET_USER_PROFILE =
            WebMvcLinkBuilder.linkTo(methodOn(UserController.class).updateUserInfo(null, null))
                    .withRel("get-user-profile");
}
