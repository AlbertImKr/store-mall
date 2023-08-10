package com.albert.commerce.api.store.ui;

import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.units.BusinessLinks;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class StoreQueryController {

    private final StoreFacade storeFacade;

    @GetMapping("/my")
    public ResponseEntity<EntityModel<StoreData>> getMyStore(Principal principal) {
        String userEmail = principal.getName();
        StoreData storeData = storeFacade.getMyStoreByUserEmail(userEmail);
        return ResponseEntity.ok()
                .body(
                        EntityModel.of(storeData)
                                .add(BusinessLinks.GET_MY_STORE_WITH_SELF, BusinessLinks.MY_STORE)
                );
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<EntityModel<StoreData>> getStore(@PathVariable String storeId) {
        StoreData storeData = storeFacade.getStoreById(DomainId.from(storeId));
        return ResponseEntity.ok().body(
                EntityModel.of(storeData).add(
                        WebMvcLinkBuilder.linkTo(StoreController.class).slash(storeId)
                                .withSelfRel(),
                        BusinessLinks.GET_STORE
                ));
    }
}
