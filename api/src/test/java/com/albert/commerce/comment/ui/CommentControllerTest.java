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

import com.albert.commerce.comment.command.application.CommentRequest;
import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.application.CommentService;
import com.albert.commerce.comment.query.domain.CommentDao;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.MockMvc;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
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
        CommentRequest commentRequest = new CommentRequest(testProductId.getId(),
                store.getStoreId().getId(), null,
                "test");

        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("commentId").exists())
                .andExpect(jsonPath("storeId").exists())
                .andExpect(jsonPath("productId").exists())
                .andExpect(jsonPath("nickname").exists())
                .andExpect(jsonPath("detail").exists())
                // restDocs
                .andDo(document("saveComment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("commentId").description("댓글 아이디"),
                                fieldWithPath("createdTime").description("댓글 생성시간"),
                                fieldWithPath("updateTime").description("댓글 업데이트시간"),
                                fieldWithPath("nickname").description("댓글 작성"),
                                fieldWithPath("parentCommentId").description("상위 comment 아이디"),
                                fieldWithPath("userId").description("작성자 아이디"),
                                fieldWithPath("storeId").description("스토어 아이디"),
                                fieldWithPath("productId").description("상품 아이디"),
                                fieldWithPath("nickname").description("유저 네이밍"),
                                fieldWithPath("detail").description("댓글 내용")
                        )

                ))
        ;
    }

    @DisplayName("ProductId 로 Comments 조회")
    @Test
    void findCommentsByProductId() throws Exception {
        // given
        String userEmail = "consumer@email.com";
        ProductId productId = product.getProductId();
        StoreId storeId = store.getStoreId();
        String productIdValue = productId.getId();
        String storeIdValue = storeId.getId();
        CommentRequest commentRequest =
                new CommentRequest(productIdValue, storeIdValue, null, "test");
        CommentResponse commentResponse1 = commentService.save(commentRequest, userEmail);
        commentRequest = new CommentRequest(
                productIdValue,
                storeIdValue,
                commentResponse1.getCommentId().getId(),
                "test1");
        CommentResponse commentResponse2 = commentService.save(commentRequest, userEmail);
        commentRequest = new CommentRequest(
                productIdValue,
                storeIdValue,
                commentResponse2.getCommentId().getId(),
                "test1");
        commentService.save(commentRequest, userEmail);

        mockMvc.perform(get("/comments")
                        .param("productId", productIdValue))
                .andDo(print())
                .andExpect(jsonPath("_embedded.comments[*].commentId").exists())
                .andExpect(jsonPath("_embedded.comments[*].storeId").exists())
                .andExpect(jsonPath("_embedded.comments[*].productId").exists())
                .andExpect(jsonPath("_embedded.comments[*].createdTime").exists())
                .andExpect(jsonPath("_embedded.comments[*].updateTime").exists())
                .andExpect(jsonPath("_embedded.comments[*].nickname").exists())
                .andExpect(jsonPath("_embedded.comments[*].detail").exists())
                .andExpect(jsonPath("_embedded.comments[*].userId").exists())
                // restDocs
                .andDo(document("findCommentsByProductId",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                responseFields(
                                        fieldWithPath("_embedded.comments[].commentId")
                                                .description("comment commentId"),
                                        fieldWithPath("_embedded.comments[].userId")
                                                .description("comment userId"),
                                        fieldWithPath("_embedded.comments[].nickname")
                                                .description("comment 작성자 nickname"),
                                        fieldWithPath("_embedded.comments[].storeId")
                                                .description("comment storeId"),
                                        fieldWithPath("_embedded.comments[].productId")
                                                .description("comment productId"),
                                        fieldWithPath("_embedded.comments[].createdTime")
                                                .description("comment createTime"),
                                        fieldWithPath("_embedded.comments[].updateTime")
                                                .description("comment updateTime"),
                                        fieldWithPath("_embedded.comments[].parentCommentId")
                                                .description("comment parentCommentId"),
                                        fieldWithPath("_embedded.comments[].detail")
                                                .description("comment detail"),
                                        subsectionWithPath("_embedded.comments[].comment")
                                                .description("child Comment").optional()
                                )
                        )
                )
        ;
    }
}
