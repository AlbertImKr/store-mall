package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.request.StoreRegisterRequest;
import com.albert.commerce.adapter.in.web.request.StoreUploadRequest;
import com.albert.commerce.adapter.in.web.security.UserEmail;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.store.StoreDeleteCommand;
import com.albert.commerce.application.service.store.StoreRegisterCommand;
import com.albert.commerce.application.service.store.StoreUploadCommand;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<Map<String, String>> register(
            @RequestBody StoreRegisterRequest storeRegisterRequest,
            @UserEmail String email
    ) {
        var storeRegisterCommand = toStoreRegisterCommand(storeRegisterRequest, email);
        String storeId = commandGateway.request(storeRegisterCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        Map.of("storeId", storeId)
                );
    }

    @PutMapping("/my")
    public ResponseEntity<Void> upload(
            @RequestBody StoreUploadRequest storeUploadRequest,
            @UserEmail String email
    ) {
        var storeUploadCommand = toStoreUploadCommand(storeUploadRequest, email);
        commandGateway.request(storeUploadCommand);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/my")
    public ResponseEntity<Void> delete(@UserEmail String email) {
        StoreDeleteCommand storeDeleteCommand = new StoreDeleteCommand(email);
        commandGateway.request(storeDeleteCommand);
        return ResponseEntity.noContent().build();
    }

    private static StoreRegisterCommand toStoreRegisterCommand(StoreRegisterRequest storeRegisterRequest,
            String userEmail) {
        return new StoreRegisterCommand(
                userEmail,
                storeRegisterRequest.storeName(),
                storeRegisterRequest.address(),
                storeRegisterRequest.phoneNumber(),
                storeRegisterRequest.email(),
                storeRegisterRequest.ownerName()
        );
    }

    private static StoreUploadCommand toStoreUploadCommand(StoreUploadRequest storeUploadRequest, String userEmail) {
        return new StoreUploadCommand(
                userEmail,
                storeUploadRequest.storeName(),
                storeUploadRequest.address(),
                storeUploadRequest.phoneNumber(),
                storeUploadRequest.email(),
                storeUploadRequest.ownerName()
        );
    }

}
