package com.albert.commerce.adapter.in.web.controller;

import com.albert.commerce.adapter.in.web.facade.StoreFacade;
import com.albert.commerce.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreFacade storeFacade;

    @GetMapping("/stores/{id}")
    public Store getById(@PathVariable String id) {
        return storeFacade.getById(id);
    }

}
