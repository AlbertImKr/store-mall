package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.dto.StoreCreateRequest;
import com.albert.commerce.adapter.in.web.dto.StoreUpdateRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.StoreCreateCommand;
import com.albert.commerce.application.service.StoreUpdateCommand;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<Map<String, String>> create(
            @RequestBody StoreCreateRequest storeCreateRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        var storeCreateCommand = new StoreCreateCommand(
                userEmail,
                storeCreateRequest.storeName(),
                storeCreateRequest.address(),
                storeCreateRequest.phoneNumber(),
                storeCreateRequest.email(),
                storeCreateRequest.ownerName()
        );
        String storeId = commandGateway.request(storeCreateCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of("storeId", storeId)
                );
    }

    @PutMapping("/my")
    public ResponseEntity<Void> update(
            @RequestBody StoreUpdateRequest storeUpdateRequest,
            Principal principal
    ) {
        String userEmail = principal.getName();
        var storeUpdateCommand = new StoreUpdateCommand(
                userEmail,
                storeUpdateRequest.storeName(),
                storeUpdateRequest.address(),
                storeUpdateRequest.phoneNumber(),
                storeUpdateRequest.email(),
                storeUpdateRequest.ownerName()
        );
        commandGateway.request(storeUpdateCommand);
        return ResponseEntity.badRequest().build();
    }

}
