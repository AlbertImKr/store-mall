package com.albert.commerce.store.ui;

import com.albert.commerce.store.application.NewStoreRequest;
import com.albert.commerce.store.application.SellerStoreResponse;
import com.albert.commerce.store.application.SellerStoreService;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class SellerStoreController {

    private final SellerStoreService sellerStoreService;
    private final UserDataDao userDataDao;
    private final StoreDao storeDao;

    @PostMapping
    public ResponseEntity createStore(@RequestBody NewStoreRequest newStoreRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        UserProfileResponse userProfileResponse = userDataDao.findByEmail(principal.getName())
                .orElseThrow();
        newStoreRequest.setUserId(userProfileResponse.getId());
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(newStoreRequest);

        URI myStore = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(SellerStoreController.class).getMyStore(principal))
                .toUri();
        String storeId = sellerStoreResponse.getStoreId().getValue();
        sellerStoreResponse.add(
                WebMvcLinkBuilder.linkTo(SellerStoreController.class)
                        .slash(storeId)
                        .withSelfRel()
        );
        return ResponseEntity.created(myStore).body(sellerStoreResponse);
    }

    @GetMapping("/my")
    public ResponseEntity getMyStore(Principal principal) {
        UserProfileResponse userProfileResponse = userDataDao.findByEmail(principal.getName())
                .orElseThrow();
        Optional<Store> store = storeDao.findByStoreUserId(
                new StoreUserId(userProfileResponse.getId()));
        if (store.isEmpty()) {
            throw new MyStoreNotFoundException();
        }
        SellerStoreResponse sellerStoreResponse = SellerStoreResponse.from(store.get());
        sellerStoreResponse.add(
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SellerStoreController.class)
                                .getMyStore(null))
                        .withSelfRel());
        return ResponseEntity.ok().body(sellerStoreResponse);
    }
}
