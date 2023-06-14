package com.albert.commerce.store.ui;

import com.albert.commerce.store.command.application.StoreRequest;
import com.albert.commerce.store.command.application.StoreResponse;
import com.albert.commerce.store.command.application.StoreService;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreController {

    private final StoreService storeService;
    private final UserDataDao userDataDao;
    private final StoreDao storeDao;

    @PostMapping
    public ResponseEntity addStore(@RequestBody StoreRequest storeRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        UserProfileResponse userProfileResponse = userDataDao.findByEmail(principal.getName())
                .orElseThrow();
        storeRequest.setUserId(userProfileResponse.getId());
        StoreResponse storeResponse = storeService.addStore(storeRequest);

        URI myStore = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(StoreController.class).getMyStore(principal))
                .toUri();
        UUID storeId = storeResponse.getStoreId().getValue();
        storeResponse.add(
                WebMvcLinkBuilder.linkTo(StoreController.class)
                        .slash(storeId)
                        .withSelfRel()
        );
        return ResponseEntity.created(myStore).body(storeResponse);
    }

    @GetMapping("/my")
    public ResponseEntity getMyStore(Principal principal) {
        UserProfileResponse userProfileResponse = userDataDao.findByEmail(principal.getName())
                .orElseThrow();
        Optional<Store> store = storeDao.findByStoreUserId(
                new StoreUserId(userProfileResponse.getId()));
        if (store.isEmpty()) {
            throw new StoreNotFoundException();
        }
        StoreResponse storeResponse = StoreResponse.from(store.get());
        storeResponse.add(
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .getMyStore(null))
                        .withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .addStore(null, null, null))
                        .withRel("add-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .getStore(null))
                        .withRel("other-store")
        );
        return ResponseEntity.ok().body(storeResponse);
    }

    @GetMapping("/{storeId}")
    public ResponseEntity getStore(@PathVariable UUID storeId) {
        Optional<Store> store = storeDao.findById(StoreId.from(storeId));
        if (store.isEmpty()) {
            throw new StoreNotFoundException();
        }
        StoreResponse storeResponse = StoreResponse.from(store.get());
        storeResponse.add(
                WebMvcLinkBuilder.linkTo(StoreController.class).slash(storeId).withSelfRel(),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .getMyStore(null))
                        .withRel("my-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .addStore(null, null, null))
                        .withRel("add-store"),
                WebMvcLinkBuilder.linkTo(
                                WebMvcLinkBuilder.methodOn(StoreController.class)
                                        .getStore(null))
                        .withRel("other-store"));
        return ResponseEntity.ok().body(storeResponse);
    }

}
