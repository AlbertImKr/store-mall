package com.albert.commerce.common;

import com.albert.commerce.store.ui.ConsumerStoreController;
import com.albert.commerce.store.ui.SellerStoreController;
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
}
