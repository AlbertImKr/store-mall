package com.albert.commerce.order.ui;

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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.product.command.application.ProductCreatedResponse;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.application.NewStoreRequest;
import com.albert.commerce.store.command.application.SellerStoreResponse;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.domain.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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


@WithMockUser("consumer@email.com")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@SpringBootTest
class OrderControllerTest {

    @Autowired
    OrderController orderController;

    @Autowired
    OrderService orderService;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    ProductService productService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductDao productDao;

    UserInfoResponse seller;
    SellerStoreResponse store;
    List<ProductId> productsId;

    @BeforeEach
    void setting() {
        userService.init("seller@email.com");
        userService.init("consumer@email.com");
        seller = userDao.findUserProfileByEmail("seller@email.com");
        NewStoreRequest newStoreRequest = new NewStoreRequest("testStoreName", "testOwner",
                "address", "01001000100",
                "test@email.com");
        store = sellerStoreService.createStore(newStoreRequest, seller.getId());
        productsId = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductRequest productRequest = new ProductRequest("product" + i, Money.from(10000L),
                    "testProduct",
                    "test", "test");
            ProductCreatedResponse product = productService.addProduct(
                    productRequest, store.getStoreId());
            productsId.add(product.getProductId());
        }
    }


    @DisplayName("주문을 생성한다")
    @Test
    void createOrder() throws Exception {
        // given
        OrderRequest orderRequest = new OrderRequest(productsId, store.getStoreId());
        // when
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest))
                        .accept(MediaTypes.HAL_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("orderId").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.order").exists())
                //
                .andDo(document("createOrder",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        links(
                                linkWithRel("self").description("현재 요청한 link"),
                                linkWithRel("order").description("생성된 order link")
                        ),
                        responseFields(
                                subsectionWithPath("_links").ignored(),
                                fieldWithPath("orderId").description("orderId")
                        )

                ))
        ;
    }


    @DisplayName("주문 생성이 필요한 테스트")
    @Nested
    class NeedOrderTest {

        Order order;
        UserInfoResponse consumer;

        @BeforeEach
        void settingOrder() {
            consumer = userDao.findUserProfileByEmail("consumer@email.com");
            List<Product> products = productDao.findProductsByProductsId(productsId,
                    store.getStoreId());
            order = orderService.createOrder(consumer.getId(), 1000, products, store.getStoreId());
        }


        @DisplayName("주문을 id로 조회한다")
        @Test
        void getOrder() throws Exception {
            mockMvc.perform(get("/orders/" + order.getOrderId().getValue()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("orderId").exists())
                    .andExpect(jsonPath("userId").value(consumer.getId().getValue()))
                    .andExpect(jsonPath("storeId").value(store.getStoreId().getValue()))
                    .andExpect(jsonPath("products").isArray())
                    .andExpect(jsonPath("products.*.createdTime").exists())
                    .andExpect(jsonPath("products.*.updateTime").exists())
                    .andExpect(jsonPath("products.*.productId").exists())
                    .andExpect(jsonPath("products.*.storeId").exists())
                    .andExpect(jsonPath("products.*.productName").exists())
                    .andExpect(jsonPath("products.*.price").exists())
                    .andExpect(jsonPath("products.*.description").exists())
                    .andExpect(jsonPath("products.*.brand").exists())
                    .andExpect(jsonPath("products.*.category").exists())
                    .andExpect(jsonPath("deliveryStatus").exists())
                    .andExpect(jsonPath("amount").exists())
                    .andExpect(jsonPath("createdTime").exists())
                    .andExpect(jsonPath("_links.self").exists())
                    .andExpect(jsonPath("products.*._links.self").exists())
                    // restdocs
                    .andDo(document("orderFindById", preprocessResponse(prettyPrint()),
                                    links(
                                            linkWithRel("self").description("현재 접속한 link")
                                    ),
                                    responseFields(
                                            subsectionWithPath("_links").ignored(),
                                            fieldWithPath("orderId").description("order id"),
                                            fieldWithPath("userId").description("order User id"),
                                            fieldWithPath("storeId").description("order store id"),
                                            fieldWithPath("products").description("주문한 상품들"),
                                            fieldWithPath("products[].createdTime").description("상품 생성 시간"),
                                            fieldWithPath("products[].updateTime").description(
                                                    "상품 업데이트 시간"),
                                            fieldWithPath("products[].productId").description("상품 아이디"),
                                            fieldWithPath("products[].storeId").description("상품 스터어 아이디"),
                                            fieldWithPath("products[].productName").description("상품 품"),
                                            fieldWithPath("products[].price").description("상품 가격"),
                                            fieldWithPath("products[].description").description("상품 상세"),
                                            fieldWithPath("products[].brand").description("상품 브랜드"),
                                            fieldWithPath("products[].category").description("상품 카테로그"),
                                            fieldWithPath("products[]._links.self.href").description(
                                                    "상품 링크"),
                                            fieldWithPath("deliveryStatus").description("배송 상태"),
                                            fieldWithPath("amount").description("총 가격"),
                                            fieldWithPath("createdTime").description("생성 시간")
                                    )
                            )
                    )
            ;
        }


    }


}
