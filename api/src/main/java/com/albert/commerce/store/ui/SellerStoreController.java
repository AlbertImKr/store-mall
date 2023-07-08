package com.albert.commerce.store.ui;

import static com.albert.commerce.common.units.BusinessLinks.GET_MY_STORE_WITH_SELF;
import static com.albert.commerce.common.units.BusinessLinks.GET_STORE_BY_STORE_ID;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.query.application.StoreFacade;
import com.albert.commerce.user.command.application.dto.UserInfoResponse;
import com.albert.commerce.user.query.application.UserFacade;
import java.net.URI;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class SellerStoreController {

    private final SellerStoreService sellerStoreService;
    private final StoreFacade storeFacade;
    private final UserFacade userFacade;

    @PostMapping
    public ResponseEntity createStore(@RequestBody NewStoreRequest newStoreRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        String userEmail = principal.getName();
        UserInfoResponse user = userFacade.findByEmail(userEmail);
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(
                newStoreRequest.toStore(user.getId()));

        URI myStore = BusinessLinks.MY_STORE.toUri();
        String storeId = sellerStoreResponse.getStoreId().getId();
        sellerStoreResponse.add(GET_STORE_BY_STORE_ID(storeId));
        return ResponseEntity.created(myStore).body(sellerStoreResponse);
    }

    @GetMapping("/my")
    public ResponseEntity getMyStore(Principal principal) {
        String userEmail = principal.getName();
        UserInfoResponse user = userFacade.findByEmail(userEmail);
        SellerStoreResponse sellerStoreResponse = storeFacade.findStoreByUserId(user.getId());

        sellerStoreResponse.add(GET_MY_STORE_WITH_SELF);
        return ResponseEntity.ok().body(sellerStoreResponse);
    }

    @PutMapping("/my")
    public ResponseEntity updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        UserInfoResponse user = userFacade.findByEmail(principal.getName());
        SellerStoreResponse sellerStoreResponse = sellerStoreService.updateMyStore(
                updateStoreRequest, user.getId());

        sellerStoreResponse.add(GET_MY_STORE_WITH_SELF);
        return ResponseEntity.ok().body(sellerStoreResponse);
    }

}
