package com.albert.commerce.store.ui;

import static com.albert.commerce.common.units.BusinessLinks.GET_MY_STORE_WITH_SELF;
import static com.albert.commerce.common.units.BusinessLinks.GET_STORE_BY_STORE_ID;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.net.URI;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
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
    private final StoreDataDao storeDataDao;
    private final UserDao userDao;

    @PostMapping
    public ResponseEntity createStore(@RequestBody NewStoreRequest newStoreRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(
                newStoreRequest.toStore(user.getUserId()));

        URI myStore = BusinessLinks.MY_STORE.toUri();
        String storeId = sellerStoreResponse.getStoreId().getId();
        sellerStoreResponse.add(GET_STORE_BY_STORE_ID(storeId));
        return ResponseEntity.created(myStore).body(sellerStoreResponse);
    }

    @GetMapping("/my")
    public ResponseEntity getMyStore(Principal principal) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        StoreData storeData = storeDataDao.findStoreByUserId(user.getUserId())
                .orElseThrow(StoreNotFoundException::new);

        return ResponseEntity.ok()
                .body(EntityModel.of(storeData)
                        .add(GET_MY_STORE_WITH_SELF, BusinessLinks.MY_STORE));
    }

    @PutMapping("/my")
    public ResponseEntity updateMyStore(@RequestBody UpdateStoreRequest updateStoreRequest,
            Principal principal) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        SellerStoreResponse sellerStoreResponse = sellerStoreService.updateMyStore(
                updateStoreRequest, user.getUserId());

        sellerStoreResponse.add(GET_MY_STORE_WITH_SELF);
        return ResponseEntity.ok().body(sellerStoreResponse);
    }

}
