package com.albert.commerce.store.command.application;

import com.albert.commerce.store.ui.StoreController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public class StoreLinks {

    private StoreLinks() {
    }

    public static final Link MY_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StoreController.class)
                            .getMyStore(null))
                    .withRel("my-store");
    public static final Link ADD_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StoreController.class)
                            .addStore(null, null, null))
                    .withRel("add-store");
    public static final Link GET_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StoreController.class)
                            .getStore(null))
                    .withRel("get-store");


}
