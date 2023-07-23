package com.albert.commerce.store.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.StoreId;
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
public class SellerStoreController {

    private final SellerStoreService sellerStoreService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createStore(@RequestBody NewStoreRequest newStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        StoreId storeId = sellerStoreService.createStore(userEmail, newStoreRequest);

        URI myStore = BusinessLinks.MY_STORE.toUri();
        return ResponseEntity.created(myStore).body(Map.of("storeId", storeId.getId()));
    }

    @PutMapping("/my")
    public ResponseEntity<Map<String, String>> updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        StoreId storeId = sellerStoreService.updateMyStore(updateStoreRequest, userEmail);
        return ResponseEntity.ok().body(Map.of("storeId", storeId.getId()));
    }

}
