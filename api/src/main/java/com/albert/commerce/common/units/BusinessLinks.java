package com.albert.commerce.common.units;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.ui.ProductController;
import com.albert.commerce.store.ui.ConsumerStoreController;
import com.albert.commerce.store.ui.SellerStoreController;
import com.albert.commerce.user.ui.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public final class BusinessLinks {

    private BusinessLinks() {
        throw new AssertionError("인스턴스화가 필요하지 않는 클래스입니다.");
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

    public static Link getProductSelfRel(ProductId productId) {
        return WebMvcLinkBuilder
                .linkTo(methodOn(ProductController.class).addProduct(null, null))
                .slash(productId.getId())
                .withSelfRel();
    }

    public static final Links USER_INFO_RESPONSE_LINKS = Links.of(
            linkTo(methodOn(UserController.class).updateUserInfo(null, null))
                    .withSelfRel(),
            BusinessLinks.CREATE_STORE,
            BusinessLinks.GET_STORE,
            BusinessLinks.MY_STORE);
}
