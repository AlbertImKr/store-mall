package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.request.ProductCreateRequest;
import com.albert.commerce.adapter.in.web.request.ProductUpdateRequest;
import com.albert.commerce.adapter.in.web.response.ProductId;
import com.albert.commerce.adapter.in.web.security.UserEmail;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.product.ProductCreateCommand;
import com.albert.commerce.application.service.product.ProductDeleteCommand;
import com.albert.commerce.application.service.product.ProductUpdateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<ProductId> register(
            @RequestBody ProductCreateRequest productCreateRequest,
            @UserEmail String userEmail
    ) {
        var productCreateCommand = toProductCreateCommand(productCreateRequest, userEmail);
        String productId = commandGateway.request(productCreateCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ProductId(productId));
    }


    @PutMapping(value = "/{productId}")
    public ResponseEntity<Void> upload(
            @UserEmail String userEmail,
            @PathVariable String productId,
            @RequestBody ProductUpdateRequest productUpdateRequest
    ) {
        var productUpdateCommand = toProductUpdateCommand(productId, productUpdateRequest, userEmail);
        commandGateway.request(productUpdateCommand);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{productId}")
    public ResponseEntity<Void> delete(
            @UserEmail String userEmail,
            @PathVariable String productId
    ) {
        var productDeleteCommand = new ProductDeleteCommand(userEmail, productId);
        commandGateway.request(productDeleteCommand);
        return ResponseEntity.noContent().build();
    }

    private static ProductCreateCommand toProductCreateCommand(ProductCreateRequest productCreateRequest,
            String userEmail) {
        return new ProductCreateCommand(
                userEmail,
                productCreateRequest.productName(),
                productCreateRequest.price(),
                productCreateRequest.description(),
                productCreateRequest.brand(),
                productCreateRequest.category()
        );
    }

    private static ProductUpdateCommand toProductUpdateCommand(String productId,
            ProductUpdateRequest productUpdateRequest, String userEmail) {
        return new ProductUpdateCommand(
                userEmail,
                productId,
                productUpdateRequest.productName(),
                productUpdateRequest.price(),
                productUpdateRequest.description(),
                productUpdateRequest.brand(),
                productUpdateRequest.category()
        );
    }
}
