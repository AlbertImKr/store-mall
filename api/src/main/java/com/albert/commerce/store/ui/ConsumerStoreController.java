package com.albert.commerce.store.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/stores", produces = MediaTypes.HAL_JSON_VALUE)
public class ConsumerStoreController {

    private final StoreDataDao storeDataDao;

    @GetMapping("/{storeId}")
    public ResponseEntity<EntityModel<StoreData>> getStore(@PathVariable String storeId) {
        StoreData storeData = storeDataDao.findById(StoreId.from(storeId))
                .orElseThrow(StoreNotFoundException::new);
        return ResponseEntity.ok().body(
                EntityModel.of(storeData).add(
                        WebMvcLinkBuilder.linkTo(SellerStoreController.class).slash(storeId)
                                .withSelfRel(),
                        BusinessLinks.GET_STORE
                ));
    }
}
