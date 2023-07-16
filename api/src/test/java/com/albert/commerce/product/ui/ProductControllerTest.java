package com.albert.commerce.product.ui;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.infra.persistence.imports.ProductJpaRepository;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.infra.persistance.imports.UserJpaRepository;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureRestDocs
@WithMockUser(username = "seller@email.com")
@AutoConfigureMockMvc
@SpringBootTest
class ProductControllerTest {

    private static final String TEST_PRODUCT_NAME = "testProductName";
    private static final String TEST_DESCRIPTION = "testDescription";
    private static final String TEST_BRAND = "testBrand";
    private static final String TEST_CATEGORY = "testCategory";
    private static final int TEST_PRICE = 10000;
    private static final String TEST_USER_EMAIL = "seller@email.com";
    private static final String TEST_STORE_NAME = "testStoreName";
    private static final String TEST_STORE_EMAIL = "test@email.com";
    private static final String TEST_OWNER = "testOwner";
    private static final String TEST_PHONE_NUMBER = "01011001100";
    private static final String TEST_ADDRESS = "testAddress";
    private static final int CHANGED_PRICE = 300;
    private static final String CHANGED_PRODUCT_NAME = "changedProductName";
    private static final String CHANGED_DESCRIPTION = "changedDescription";
    private static final String CHANGED_CATEGORY = "changedCategory";
    private static final String CHANGED_BRAND = "changedBrand";
    private static final String NO_MATCH_ID = "noMatchId";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserDao userDao;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    ProductService productService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserService userService;

    @Autowired
    StoreJpaRepository storeJpaRepository;

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    ProductJpaRepository productJpaRepository;

    UserData user;

    @BeforeEach
    void saveTestUser() {
        userService.createByEmail(TEST_USER_EMAIL);
        user = userDao.findByEmail(TEST_USER_EMAIL).orElseThrow(UserNotFoundException::new);
    }

    @AfterEach
    void clear() {
        storeJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        productJpaRepository.deleteAll();
    }

    @DisplayName("My Store에서 Product를 추가한다")
    @Test
    void addProduct() throws Exception {
        ProductRequest productRequest = new ProductRequest(
                TEST_PRODUCT_NAME, TEST_PRICE, TEST_DESCRIPTION, TEST_BRAND,
                TEST_CATEGORY
        );
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_STORE_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();

        sellerStoreService.createStore(user.getEmail(), newStoreRequest);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("productName").value(TEST_PRODUCT_NAME))
                .andExpect(jsonPath("price").value(TEST_PRICE))
                .andExpect(jsonPath("description").value(TEST_DESCRIPTION))
                .andExpect(jsonPath("brand").value(TEST_BRAND))
                .andExpect(jsonPath("category").value(TEST_CATEGORY))
                .andExpect(jsonPath("_links.self").exists())
                //restDocs
                .andDo(document(
                                "addProduct", preprocessResponse(prettyPrint()),
                                links(
                                        halLinks(),
                                        linkWithRel("self").description("현재 product 링크")
                                ),
                                responseFields(
                                        subsectionWithPath("_links").ignored(),
                                        fieldWithPath("productId").description("상품 아이디"),
                                        fieldWithPath("productName").description("상품 네이밍"),
                                        fieldWithPath("price").description("상품 가격"),
                                        fieldWithPath("description").description("상품 설명"),
                                        fieldWithPath("brand").description("상품 브랜드"),
                                        fieldWithPath("category").description("상품 카타고리")
                                )
                        )
                );
    }

    @DisplayName("Product를 update한다")
    @Test
    void updateProductSuccess() throws Exception {
        // given
        ProductRequest productRequest = new ProductRequest(
                TEST_PRODUCT_NAME,
                TEST_PRICE,
                TEST_DESCRIPTION,
                TEST_BRAND,
                TEST_CATEGORY
        );
        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                .storeName(TEST_STORE_NAME)
                .email(TEST_STORE_EMAIL)
                .ownerName(TEST_OWNER)
                .phoneNumber(TEST_PHONE_NUMBER)
                .address(TEST_ADDRESS)
                .build();

        StoreId storeId = sellerStoreService.createStore(user.getEmail(),
                newStoreRequest);

        ProductCreatedResponse productCreatedResponse =
                productService.addProduct(user.getEmail(), productRequest);
        productRequest = new ProductRequest(
                CHANGED_PRODUCT_NAME,
                CHANGED_PRICE,
                CHANGED_DESCRIPTION,
                CHANGED_BRAND,
                CHANGED_CATEGORY
        );

        // when,then
        mockMvc.perform(put("/products/" + productCreatedResponse.getProductId().getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("productName").value(CHANGED_PRODUCT_NAME))
                .andExpect(jsonPath("price").value(CHANGED_PRICE))
                .andExpect(jsonPath("description").value(CHANGED_DESCRIPTION))
                .andExpect(jsonPath("brand").value(CHANGED_BRAND))
                .andExpect(jsonPath("category").value(CHANGED_CATEGORY))
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.my-store").exists())
                // restdocs¬
                .andDo(document("updateProductSuccess",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(
                                halLinks(),
                                linkWithRel("self").description("현재 연결한 link"),
                                linkWithRel("my-storeId").description("my-storeId link")
                        ),
                        responseFields(
                                subsectionWithPath("_links").ignored(),
                                fieldWithPath("productId").description("상품 아이디"),
                                fieldWithPath("storeId").description("스토어 아이"),
                                fieldWithPath("price").description("상품 가격"),
                                fieldWithPath("productName").description("상품명"),
                                fieldWithPath("description").description("상품 상세"),
                                fieldWithPath("brand").description("상품 브랜드"),
                                fieldWithPath("createdTime").description("생성시간"),
                                fieldWithPath("category").description("카테코리"),
                                fieldWithPath("updateTime").description("업데이트시간")
                        )
                ))
        ;
    }


    @DisplayName("Product를 update 실패한다")
    @Test
    void updateProductFailed() throws Exception {
        ProductRequest productRequest = new ProductRequest(
                CHANGED_PRODUCT_NAME,
                CHANGED_PRICE,
                CHANGED_DESCRIPTION,
                CHANGED_BRAND,
                CHANGED_CATEGORY
        );
        mockMvc.perform(put("/products/" + NO_MATCH_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequest)))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("error-message").exists())
                // restdocs
                .andDo(document("updateProductFailed",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("error-message").description("에러 메시지")
                        )
                ))
        ;
    }

    @DisplayName("데이터 추가")
    @Nested
    class Products {

        @Transactional
        @BeforeEach
        void setData() {
            ProductRequest productRequest = new ProductRequest(
                    TEST_PRODUCT_NAME, TEST_PRICE, TEST_DESCRIPTION, TEST_BRAND,
                    TEST_CATEGORY
            );
            NewStoreRequest newStoreRequest = NewStoreRequest.builder()
                    .storeName(TEST_STORE_NAME)
                    .email(TEST_STORE_EMAIL)
                    .ownerName(TEST_OWNER)
                    .phoneNumber(TEST_PHONE_NUMBER)
                    .address(TEST_ADDRESS)
                    .build();
            StoreId storeId = sellerStoreService.createStore(user.getEmail(),
                    newStoreRequest);
            for (int i = 0; i < 100; i++) {
                productService.addProduct(user.getEmail(), productRequest);
            }
        }

        @DisplayName("My Store에서 모든 Product를 가져온다")
        @Test
        void getProducts() throws Exception {

            mockMvc.perform(get("/products")
                            .param("size", "20")
                            .param("page", "1")
                            .accept(MediaTypes.HAL_JSON)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("_embedded.productResponseList").isArray())
                    .andExpect(jsonPath("_embedded.productResponseList.*.productId").exists())
                    .andExpect(jsonPath("_embedded.productResponseList.*.productName").exists())
                    .andExpect(jsonPath("_embedded.productResponseList.*.price").exists())
                    .andExpect(jsonPath("_embedded.productResponseList.*.description").exists())
                    .andExpect(jsonPath("_embedded.productResponseList.*.brand").exists())
                    .andExpect(jsonPath("_embedded.productResponseList.*.category").exists())
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("_links.first").exists())
                    .andExpect(jsonPath("_links.prev").exists())
                    .andExpect(jsonPath("_links.next").exists())
                    .andExpect(jsonPath("_links.last").exists())
                    // restDocs
                    .andDo(document("getProducts", preprocessResponse(prettyPrint()),
                                    links(
                                            halLinks(),
                                            linkWithRel("self").description("요청한 링크"),
                                            linkWithRel("first").description("첫 페이지 링크"),
                                            linkWithRel("prev").description("이전 페이지 링크"),
                                            linkWithRel("next").description("다음 페이지 링크"),
                                            linkWithRel("last").description("My Store 연결 링크")
                                    ),
                                    responseFields(
                                            subsectionWithPath("_links").ignored(),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[]._links.self.href").description(
                                                    "product 링크"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].productId").description(
                                                    "상품 아이디"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].productName").description(
                                                    "상품 네이밍"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].price").description(
                                                    "상품 가격"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].brand").description(
                                                    "상품 브랜드"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].storeId").description(
                                                    "상품 아이디"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].category").description(
                                                    "상품의 카테고리"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].description").description(
                                                    "상품 설명"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].createdTime").description(
                                                    "생성 시간"),
                                            fieldWithPath(
                                                    "_embedded.productResponseList[].updateTime").description(
                                                    "업데이트 시간"),
                                            fieldWithPath("page.size").description("page size"),
                                            fieldWithPath("page.totalElements").description(
                                                    "전체 개수"),
                                            fieldWithPath("page.totalPages").description(
                                                    "전체 페이지 수"),
                                            fieldWithPath("page.number").description("현재 페이지 넘버")
                                    )
                            )
                    );
        }
    }
}
