package com.albert.commerce.order.ui;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.order.command.application.DeleteOrderRequest;
import com.albert.commerce.order.command.application.OrderNotFoundException;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderFacade;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.dto.ProductCreatedResponse;
import com.albert.commerce.product.command.application.dto.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.product.query.domain.ProductDao;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;


@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@WithMockUser("consumer@email.com")
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@SpringBootTest
class OrderControllerTest {

    public static final String SELLER_EMAIL = "seller@email.com";
    public static final String CONSUMER_EMAIL = "consumer@email.com";
    @Autowired
    OrderController orderController;

    @Autowired
    OrderFacade orderFacade;

    @Autowired
    OrderService orderService;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;
    @Autowired
    ProductService productService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ProductDao productDao;

    @Autowired
    ProductFacade productFacade;

    @Autowired
    EntityManager entityManager;

    UserData seller;
    UserData consumer;
    SellerStoreResponse store;
    Map<String, Long> requestProductsId;
    List<ProductId> productIds;
    OrderRequest orderRequest;

    @BeforeEach
    void setting() {
        userService.createByEmail(SELLER_EMAIL);
        userService.createByEmail(CONSUMER_EMAIL);
        seller = userDao.findByEmail(SELLER_EMAIL).orElseThrow(UserNotFoundException::new);
        consumer = userDao.findByEmail(CONSUMER_EMAIL).orElseThrow(UserNotFoundException::new);
        NewStoreRequest newStoreRequest = new NewStoreRequest("testStoreName", "testOwner",
                "address", "01001000100",
                "test@email.com");
        store = sellerStoreService.createStore(newStoreRequest.toStore(consumer.getUserId()));
        requestProductsId = new HashMap<>();
        productIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ProductRequest productRequest = new ProductRequest("product" + i, 10000,
                    "testProduct",
                    "test", "test");
            ProductCreatedResponse product = productService.addProduct(
                    productRequest.toProduct(store.getStoreId()));
            productIds.add(product.getProductId());
            requestProductsId.put(product.getProductId().getId(), (long) i);
        }
        orderRequest = new OrderRequest(requestProductsId, store.getStoreId().getId());
    }


    @DisplayName("주문을 생성한다")
    @Test
    void createOrder() throws Exception {
        // given

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

        OrderId orderId;
        UserData consumer;

        @BeforeEach
        void settingOrder() {
            consumer = userDao.findByEmail("consumer@email.com")
                    .orElseThrow(UserNotFoundException::new);
            orderId = orderService.placeOrder("consumer@email.com", orderRequest);
        }


        @DisplayName("주문을 id로 조회한다")
        @Test
        void getOrder() throws Exception {
            mockMvc.perform(get("/orders/" + orderId.getId()))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("orderId").exists())
                    .andExpect(jsonPath("userId").value(consumer.getUserId().getId()))
                    .andExpect(jsonPath("storeId").value(store.getStoreId().getId()))
                    .andExpect(jsonPath("deliveryStatus").exists())
                    .andExpect(jsonPath("orderLineDetails").isArray())
                    .andExpect(jsonPath("orderLineDetails.*.productId").exists())
                    .andExpect(jsonPath("orderLineDetails.*.price").exists())
                    .andExpect(jsonPath("orderLineDetails.*.quantity").exists())
                    .andExpect(jsonPath("orderLineDetails.*.amount").exists())
                    .andExpect(jsonPath("orderLineDetails.*.productName").exists())
                    .andExpect(jsonPath("orderLineDetails.*.productDescription").exists())
                    .andExpect(jsonPath("createdTime").hasJsonPath())
//                    .andExpect(jsonPath("_links.self").exists())
//                    .andExpect(jsonPath("products.*._links.self").exists())
            // restdocs
//                    .andDo(document("orderFindById", preprocessResponse(prettyPrint()),
////                                    links(
////                                            linkWithRel("self").description("현재 접속한 link")
////                                    ),
//                                    responseFields(
////                                            subsectionWithPath("_links").ignored(),
//                                            fieldWithPath("orderId").description("order id"),
//                                            fieldWithPath("userId").description("order User id"),
//                                            fieldWithPath("storeId").description("order store id"),
//                                            fieldWithPath("productsIdAndQuantity").description("주문한 상품들"),
//                                            fieldWithPath("productsIdAndQuantity[].createdTime").description("상품 생성 시간"),
//                                            fieldWithPath("productsIdAndQuantity[].updateTime").description(
//                                                    "상품 업데이트 시간"),
//                                            fieldWithPath("productsIdAndQuantity[].productId").description("상품 아이디"),
//                                            fieldWithPath("productsIdAndQuantity[].storeId").description("상품 스터어 아이디"),
//                                            fieldWithPath("productsIdAndQuantity[].productName").description("상품 품"),
//                                            fieldWithPath("productsIdAndQuantity[].price").description("상품 가격"),
//                                            fieldWithPath("productsIdAndQuantity[].description").description("상품 상세"),
//                                            fieldWithPath("productsIdAndQuantity[].brand").description("상품 브랜드"),
//                                            fieldWithPath("productsIdAndQuantity[].category").description("상품 카테로그"),
//                                            fieldWithPath("productsIdAndQuantity[]._links.self.href").description(
//                                                    "상품 링크"),
//                                            fieldWithPath("deliveryStatus").description("배송 상태"),
//                                            fieldWithPath("amount").description("총 가격"),
//                                            fieldWithPath("createdTime").description("생성 시간")
//                                    )
//                            )
//                    )
            ;
        }

        @DisplayName("주문을 삭제한다")
        @Test
        void deleteOrder() throws Exception {
            // given
            DeleteOrderRequest deleteOrderRequest = new DeleteOrderRequest(orderId.getId(), "wrong order");

            // when
            mockMvc.perform(delete("/orders")
                            .content(objectMapper.writeValueAsString(deleteOrderRequest))
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());

            assertThatThrownBy(() -> orderFacade.findById(orderId, consumer.getEmail())).isInstanceOf(
                    OrderNotFoundException.class);
        }
    }

    @DisplayName("유저가 많은 orders를 하였다")
    @Nested
    class EnoughOrders {

        @BeforeEach
        void setOrders() {
            for (int i = 0; i < 100; i++) {
                orderService.placeOrder("consumer@email.com", orderRequest);
            }
        }

        @DisplayName("유저의 모든 주문을 가져온다")
        @Test
        void getAllOrders() throws Exception {
            mockMvc.perform(get("/orders")
                            .param("page", "2")
                            .param("size", "20")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaTypes.HAL_JSON))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("content.*.orderId").exists())
                    .andExpect(jsonPath("content.*.userId").exists())
                    .andExpect(jsonPath("content.*.storeId").exists())
                    .andExpect(jsonPath("content.*.deliveryStatus").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails").isArray())
                    .andExpect(jsonPath("content.*.createdTime").hasJsonPath())
                    .andExpect(jsonPath("content.*.storeId").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.productId").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.price").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.quantity").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.amount").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.productName").exists())
                    .andExpect(jsonPath("content.*.orderLineDetails.*.productDescription").exists())
                    .andExpect(jsonPath("pageable.pageSize").value(20))
                    .andExpect(jsonPath("pageable.offset").exists())
                    .andExpect(jsonPath("pageable.pageNumber").value(2))
                    .andExpect(jsonPath("numberOfElements").value(20))
                    .andExpect(jsonPath("totalPages").exists())
//                    // restdocs
//                    .andDo(document("getAllOrders", preprocessResponse(prettyPrint()),
//                            links(
//                                    linkWithRel("self").description("현재 요청 link"),
//                                    linkWithRel("first").description("첫 페이지 link"),
//                                    linkWithRel("next").description("다음 페이지 link"),
//                                    linkWithRel("prev").description("이전 페이지 link"),
//                                    linkWithRel("last").description("마지막 페이지 link")
//                            ),
//                            responseFields(
//                                    subsectionWithPath("_links").ignored(),
//                                    fieldWithPath("page.totalPages").description("전체 페이지 개수"),
//                                    fieldWithPath("page.size").description("페이지 사이즈"),
//                                    fieldWithPath("page.number").description("현재 페이지 넘버"),
//                                    fieldWithPath("page.totalElements").description(
//                                            "전체 elements 개수"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].orderId").description(
//                                            "오더 아이디"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].userId").description(
//                                            "오더 유저 아이디"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].storeId").description(
//                                            "오더 store 아이디"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].productId").description(
//                                            "order에 포함한 product의 id"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].productName").description(
//                                            "order에 포함한 product의 name"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].price").description(
//                                            "order에 포함한 product의 price"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].description").description(
//                                            "order에 포함한 product의 description"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].brand").description(
//                                            "order에 포함한 product의 brand"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].category").description(
//                                            "order에 포함한 product의 category"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].createdTime").description(
//                                            "order에 포함한 product의 생성시간"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].updateTime").description(
//                                            "order에 포함한 product의 업데이트 시간"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[].storeId").description(
//                                            "order에 포함한 product의 스터어 아이디"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].productsIdAndQuantity[]._links.self.href").description(
//                                            "order에 포함한 product의 링크"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].deliveryStatus").description(
//                                            "order의 deliveryStatus"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].amount").description(
//                                            "order의 ammount 금액"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[].createdTime").description(
//                                            "오더 생성 시간"),
//                                    fieldWithPath(
//                                            "_embedded.orderResponseEntityList[]._links.self.href").description(
//                                            "해당 오더 링크")
//                            )))
            ;
        }
    }


}
