package com.albert.commerce.comment.ui;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.albert.commerce.comment.command.apllication.CommentRequest;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.product.command.application.ProductCreatedResponse;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.domain.ProductId;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureRestDocs
@WithMockUser("consumer@email.com")
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    SellerStoreService sellerStoreService;

    @Autowired
    ObjectMapper objectMapper;

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


    @DisplayName("Comment 정보를 받고 자장하고 저장된 Comment Info를 반환한다")
    @Test
    void saveComment() throws Exception {
        ProductId testProductId = productsId.get(0);
        CommentRequest commentRequest = new CommentRequest(testProductId, store.getStoreId(), null);

        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("commentId").exists())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("userName").exists())
                // restDocs
                .andDo(document("saveComment",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()),
                        responseFields(
                                fieldWithPath("commentId").description("댓글 아이디"),
                                fieldWithPath("storeId").description("스토어 아이디"),
                                fieldWithPath("productId").description("상품 아이디"),
                                fieldWithPath("userName").description("유저 네이밍"),
                                fieldWithPath("parentComment").description("부모 댓글"),
                                fieldWithPath("childComment").description("자식 댓글")
                        )

                ))
        ;
    }
}
