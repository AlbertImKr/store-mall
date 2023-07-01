package com.albert.commerce.comment.ui;

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

import com.albert.commerce.comment.command.apllication.CommentRequest;
import com.albert.commerce.comment.command.apllication.CommentResponse;
import com.albert.commerce.comment.command.apllication.CommentService;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.product.command.application.ProductCreatedResponse;
import com.albert.commerce.product.command.application.ProductRequest;
import com.albert.commerce.product.command.application.ProductService;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.application.NewStoreRequest;
import com.albert.commerce.store.command.application.SellerStoreResponse;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.command.application.UserService;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.domain.UserDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
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

    @Autowired
    CommentService commentService;

    @Autowired
    ProductDao productDao;

    @Autowired
    StoreDao storeDao;

    @Autowired
    CommentDao commentDao;

    User seller;
    User consumer;

    Store store;

    Product product;


    @BeforeEach
    void setting() {
        userService.init("seller@email.com");
        userService.init("consumer@email.com");
        seller = userDao.findUserProfileByEmail("seller@email.com");
        consumer = userDao.findUserProfileByEmail("consumer@email.com");
        NewStoreRequest newStoreRequest = new NewStoreRequest("testStoreName", "testOwner",
                "address", "01001000100",
                "test@email.com");
        SellerStoreResponse createdStore = sellerStoreService.createStore(newStoreRequest,
                seller.getId());
        store = storeDao.findById(createdStore.getStoreId());

        ProductRequest productRequest = new ProductRequest("product", Money.from(10000L),
                "testProduct",
                "test", "test");
        ProductCreatedResponse productCreatedResponse = productService.addProduct(
                productRequest, store.getStoreId());
        product = productDao.findById(productCreatedResponse.getProductId());
    }


    @DisplayName("Comment 정보를 받고 자장하고 저장된 Comment Info를 반환한다")
    @Test
    void saveComment() throws Exception {
        ProductId testProductId = product.getProductId();
        CommentRequest commentRequest = new CommentRequest(testProductId, store.getStoreId(), null,
                "test");

        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("commentId").exists())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("userName").exists())
                .andExpect(jsonPath("detail").exists())
                // restDocs
                .andDo(document("saveComment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("commentId").description("댓글 아이디"),
                                fieldWithPath("storeId").description("스토어 아이디"),
                                fieldWithPath("productId").description("상품 아이디"),
                                fieldWithPath("userName").description("유저 네이밍"),
                                fieldWithPath("detail").description("댓글 내용")
                        )

                ))
        ;
    }

    @DisplayName("ProductId 로 Comments 조회")
    @Test
    void findCommentsByProductId() throws Exception {
        // given
        ProductId productId = product.getProductId();
        UserId userId = consumer.getId();
        StoreId storeId = store.getStoreId();
        CommentResponse commentResponse1 = commentService.save(userId,
                productId, storeId, "test1");

        CommentResponse commentResponse2 = commentService.save(userId,
                productId, storeId, "test2", commentResponse1.getCommentId());
        commentService.save(userId,
                productId, storeId, "test3", commentResponse2.getCommentId());

        mockMvc.perform(get("/comments")
                        .param("productId", productId.getId()))
                .andDo(print())
                .andExpect(jsonPath("_embedded.commentList[*].createdTime").isArray())
                .andExpect(jsonPath("_embedded.commentList[*].updateTime").exists())
                .andExpect(jsonPath("_embedded.commentList[*].commentId").exists())
                .andExpect(jsonPath("_embedded.commentList[*].productId").exists())
                .andExpect(jsonPath("_embedded.commentList[*].storeId").exists())
                .andExpect(jsonPath("_embedded.commentList[*].userId").exists())
                .andExpect(jsonPath("_embedded.commentList[*].parentComment").exists())
                // restDocs
                .andDo(document("findCommentsByProductId",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("_embedded.commentList[].createdTime")
                                                .description("comment createTime"),
                                        fieldWithPath("_embedded.commentList[].updateTime")
                                                .description("comment updateTime"),
                                        fieldWithPath("_embedded.commentList[].commentId")
                                                .description("comment commentId"),
                                        fieldWithPath("_embedded.commentList[].productId")
                                                .description("comment productId"),
                                        fieldWithPath("_embedded.commentList[].storeId")
                                                .description("comment storeId"),
                                        fieldWithPath("_embedded.commentList[].userId")
                                                .description("comment userId"),
                                        fieldWithPath("_embedded.commentList[].detail")
                                                .description("comment detail"),
                                        subsectionWithPath("_embedded.commentList[].parentComment")
                                                .description("parent Comment").optional()
                                )
                        )
                )
        ;
    }
}
