package com.albert.commerce.command.interfaces;

import com.albert.commerce.command.application.store.StoreService;
import com.albert.commerce.command.application.store.dto.NewStoreRequest;
import com.albert.commerce.command.application.store.dto.UpdateStoreRequest;
import com.albert.commerce.command.domain.store.StoreId;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
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

    private final StoreService storeService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createStore(@RequestBody NewStoreRequest newStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        StoreId storeId = storeService.createStore(userEmail, newStoreRequest);

        return ResponseEntity.ok().body(Map.of("storeId", storeId.getValue()));
    }

    @PutMapping("/my")
    public ResponseEntity<Void> updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        storeService.updateMyStore(updateStoreRequest, userEmail);
        return ResponseEntity.ok().build();
    }

}
