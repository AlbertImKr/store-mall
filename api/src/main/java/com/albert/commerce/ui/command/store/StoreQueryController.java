package com.albert.commerce.ui.command.store;

import static com.albert.commerce.common.units.BusinessLinks.GET_MY_STORE_WITH_SELF;

import com.albert.commerce.application.query.store.StoreFacade;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.query.store.StoreData;
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
                                .add(GET_MY_STORE_WITH_SELF, BusinessLinks.MY_STORE)
                );
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<EntityModel<StoreData>> getStore(@PathVariable String storeId) {
        StoreData storeData = storeFacade.getStoreById(StoreId.from(storeId));
        return ResponseEntity.ok().body(
                EntityModel.of(storeData).add(
                        WebMvcLinkBuilder.linkTo(SellerStoreController.class).slash(storeId)
                                .withSelfRel(),
                        BusinessLinks.GET_STORE
                ));
    }
}
