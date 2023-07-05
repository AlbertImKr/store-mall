package com.albert.commerce.store.ui;

import static com.albert.commerce.common.units.BusinessLinks.GET_MY_STORE_WITH_SELF;
import static com.albert.commerce.common.units.BusinessLinks.GET_STORE_BY_STORE_ID;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.command.application.NewStoreRequest;
import com.albert.commerce.store.command.application.SellerStoreResponse;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
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
    private final StoreDao storeDao;
    private final UserDao userDao;

    @PostMapping
    public ResponseEntity createStore(@RequestBody NewStoreRequest newStoreRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        String userEmail = principal.getName();
        User user = userDao.findUserProfileByEmail(userEmail);
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(newStoreRequest,
                user.getId());

        URI myStore = BusinessLinks.MY_STORE.toUri();
        String storeId = sellerStoreResponse.getStoreId().getId();
        sellerStoreResponse.add(GET_STORE_BY_STORE_ID(storeId));
        return ResponseEntity.created(myStore).body(sellerStoreResponse);
    }

    @GetMapping("/my")
    public ResponseEntity getMyStore(Principal principal) {
        Store store = storeDao.findStoreByUserEmail(principal.getName());
        SellerStoreResponse sellerStoreResponse = SellerStoreResponse.from(store);

        sellerStoreResponse.add(GET_MY_STORE_WITH_SELF);
        return ResponseEntity.ok().body(sellerStoreResponse);
    }

    @PutMapping("/my")
    public ResponseEntity updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        SellerStoreResponse sellerStoreResponse = sellerStoreService.updateMyStore(
                updateStoreRequest, principal.getName());

        sellerStoreResponse.add(GET_MY_STORE_WITH_SELF);
        return ResponseEntity.ok().body(sellerStoreResponse);
    }

}
