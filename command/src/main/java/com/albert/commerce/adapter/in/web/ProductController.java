package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.request.ProductCreateRequest;
import com.albert.commerce.adapter.in.web.request.ProductUpdateRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.product.ProductCreateCommand;
import com.albert.commerce.application.service.product.ProductUpdateCommand;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> register(
            @RequestBody ProductCreateRequest productCreateRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        var productCreateCommand = toProductCreateCommand(productCreateRequest, userEmail);
        String productId = commandGateway.request(productCreateCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("productId", productId));
    }


    @PutMapping(value = "/{productId}")
    public ResponseEntity<Void> upload(
            Principal principal,
            @PathVariable String productId,
            @RequestBody ProductUpdateRequest productUpdateRequest
    ) {
        String userEmail = principal.getName();
        var productUpdateCommand = toProductUpdateCommand(productId, productUpdateRequest, userEmail);
        commandGateway.request(productUpdateCommand);
        return ResponseEntity.ok().build();
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
