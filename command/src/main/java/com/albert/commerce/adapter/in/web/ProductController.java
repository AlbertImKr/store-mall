package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.dto.ProductCreateRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.ProductCreateCommand;
import com.albert.commerce.application.service.ProductUpdateCommand;
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
    public ResponseEntity<Map<String, String>> create(
            @RequestBody ProductCreateRequest productCreateRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        ProductCreateCommand productCreateCommand = new ProductCreateCommand(
                userEmail,
                productCreateRequest.productName(),
                productCreateRequest.price(),
                productCreateRequest.description(),
                productCreateRequest.brand(),
                productCreateRequest.category()
        );
        String productId = commandGateway.request(productCreateCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("productId", productId));
    }


    @PutMapping(value = "/{productId}")
    public ResponseEntity<Void> update(
            Principal principal,
            @PathVariable String productId,
            @RequestBody ProductCreateRequest productCreateRequest
    ) {
        String userEmail = principal.getName();
        ProductUpdateCommand productUpdateCommand = new ProductUpdateCommand(
                userEmail,
                productId,
                productCreateRequest.productName(),
                productCreateRequest.price(),
                productCreateRequest.description(),
                productCreateRequest.brand(),
                productCreateRequest.category()
        );
        commandGateway.request(productUpdateCommand);
        return ResponseEntity.ok().build();
    }
}
