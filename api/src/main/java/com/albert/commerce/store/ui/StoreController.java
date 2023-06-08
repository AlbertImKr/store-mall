package com.albert.commerce.store.ui;

import com.albert.commerce.store.command.application.StoreRequest;
import com.albert.commerce.store.command.application.StoreService;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.query.UserDataDao;
import com.albert.commerce.user.query.UserProfileResponse;
import jakarta.validation.Valid;
import java.net.URI;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Controller
public class StoreController {

    private final StoreService storeService;
    private final UserDataDao userDataDao;

    @PostMapping("/stores")
    public ResponseEntity addStore(@Valid @RequestBody StoreRequest storeRequest, Errors errors,
            Principal principal) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }
        UserProfileResponse userProfileResponse = userDataDao.findByEmail(principal.getName())
                .orElseThrow();
        storeRequest.setUserId(userProfileResponse.getId());
        StoreId storeId = storeService.addStore(storeRequest);
        return ResponseEntity.created(URI.create("/")).body(storeId);
    }

}
