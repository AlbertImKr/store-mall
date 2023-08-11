package com.albert.commerce.api.store.ui;

import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.units.BusinessLinks;
import java.net.URI;
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
        DomainId storeId = storeService.createStore(userEmail, newStoreRequest);

        URI myStore = BusinessLinks.MY_STORE.toUri();
        return ResponseEntity.created(myStore).body(Map.of("storeId", storeId.getValue()));
    }

    @PutMapping("/my")
    public ResponseEntity<Void> updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        storeService.updateMyStore(updateStoreRequest, userEmail);
        return ResponseEntity.ok().build();
    }

}