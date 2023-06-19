package com.albert.commerce.product.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.product.command.application.ProductAssembler;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductResponse;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.application.ProdutCreatedResponse;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.StoreDao;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/products", produces = MediaTypes.HAL_JSON_VALUE)
public class ProductController {

    private final ProductService productService;
    private final StoreDao storeDao;
    private final ProductDao productDao;
    private final PagedResourcesAssembler<Product> pagedResourcesAssembler;
    private final ProductAssembler productAssembler;

    @PostMapping
    public ResponseEntity<ProdutCreatedResponse> addProduct(
            @RequestBody ProductRequest productRequest,
            Principal principal) {
        StoreId storeId = storeDao.findStoreIdByUserEmail(principal.getName());
        ProdutCreatedResponse productResponse = productService.addProduct(productRequest, storeId);

        ProductId productId = productResponse.getProductId();
        Link selfRel = BusinessLinks.getProductSelfRel(productId);
        productResponse.add(selfRel, BusinessLinks.MY_STORE);
        return ResponseEntity.created(selfRel.toUri()).body(productResponse);
    }


    @GetMapping
    public ResponseEntity<PagedModel<ProductResponse>> getAllProducts(Principal principal,
            Pageable pageable) {
        Page<Product> products =
                productDao.findProductsByUserEmail(principal.getName(), pageable);

        PagedModel<ProductResponse> productResponses = pagedResourcesAssembler
                .toModel(products, productAssembler);

        return ResponseEntity.ok(productResponses);
    }
}
