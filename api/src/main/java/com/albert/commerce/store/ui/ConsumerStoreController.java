package com.albert.commerce.store.ui;

import com.albert.commerce.store.command.application.ConsumerStoreResponse;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.StoreDao;
import lombok.RequiredArgsConstructor;
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

    private final StoreDao storeDao;

    @GetMapping("/{storeId}")
    public ResponseEntity getStore(@PathVariable String storeId) {
        Store store = storeDao.findById(StoreId.from(storeId));
        ConsumerStoreResponse consumerStoreResponse = ConsumerStoreResponse.from(store);
        consumerStoreResponse.add(
                WebMvcLinkBuilder.linkTo(SellerStoreController.class).slash(storeId).withSelfRel());
        return ResponseEntity.ok().body(consumerStoreResponse);
    }
}
