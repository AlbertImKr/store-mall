package com.albert.commerce.command.units;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.albert.commerce.command.domain.order.OrderId;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.interfaces.OrderController;
import com.albert.commerce.command.interfaces.ProductController;
import com.albert.commerce.command.interfaces.StoreController;
import com.albert.commerce.command.interfaces.UserController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

public final class BusinessLinks {

    private BusinessLinks() {
        throw new AssertionError("인스턴스화가 필요하지 않는 클래스입니다.");
    }

    public static final Link CREATE_STORE =
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StoreController.class)
                            .createStore(null, null))
                    .withRel("create-store");
    public static final Link GET_USER_PROFILE =
            WebMvcLinkBuilder.linkTo(
                            methodOn(UserController.class).updateUserInfo(null, null))
                    .withRel("get-user-profile");
    public static final Links USER_INFO_RESPONSE_LINKS = Links.of(
            linkTo(methodOn(UserController.class).updateUserInfo(null, null))
                    .withSelfRel(),
            BusinessLinks.CREATE_STORE);


    public static Link getProductSelfRel(ProductId productId) {
        return WebMvcLinkBuilder
                .linkTo(methodOn(ProductController.class).addProduct(null, null))
                .slash(productId.getValue())
                .withSelfRel();
    }


    public static Link getOrder(OrderId orderId) {
        return WebMvcLinkBuilder.linkTo(OrderController.class)
                .slash(orderId.getValue())
                .withRel("order");
    }

    public static final Link CREATE_ORDER_LINK = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(OrderController.class)
                            .placeOrder(null, null))
            .withSelfRel();


}
