package com.albert.commerce.comment.ui;

import static org.assertj.core.api.Assertions.assertThat;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.albert.commerce.api.comment.command.application.CommentRequest;
import com.albert.commerce.api.comment.command.application.CommentService;
import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.comment.query.domain.CommentDao;
import com.albert.commerce.api.product.ProductNotFoundException;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.product.command.application.dto.ProductRequest;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.query.domain.ProductDao;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.query.domain.StoreDataDao;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
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

    public static final String SELLER_EMAIL = "seller@email.com";
    public static final String CONSUMER_EMAIL = "consumer@email.com";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;


    @Autowired
    StoreService storeService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    CommentService commentService;

    @Autowired
    ProductDao productDao;

    @Autowired
    StoreDataDao storeDao;

    @Autowired
    CommentDao commentDao;

    UserData seller;
    UserData consumer;

    Store store;

    ProductData product;


    @BeforeEach
    void setting() {
        userService.createByEmail(SELLER_EMAIL);
        userService.createByEmail(CONSUMER_EMAIL);
        seller = userDao.findByEmail(SELLER_EMAIL).orElseThrow(UserNotFoundException::new);
        consumer = userDao.findByEmail("consumer@email.com")
                .orElseThrow(UserNotFoundException::new);
        NewStoreRequest newStoreRequest = new NewStoreRequest("testStoreName", "testOwner",
                "address", "01001000100",
                SELLER_EMAIL);
        storeService.createStore(seller.getEmail(), newStoreRequest);
        store = storeService.getStoreByUserEmail(seller.getUserId());

        ProductRequest productRequest = new ProductRequest("product", 10000,
                "testProduct",
                "test", "test");
        ProductId productId = productService.addProduct(seller.getEmail(),
                productRequest);
        product = productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }


    @DisplayName("Comment 정보를 받고 자장하고 저장된 Comment Info를 반환한다")
    @Test
    void saveComment() throws Exception {
        ProductId testProductId = product.getProductId();
        CommentRequest commentRequest = new CommentRequest(testProductId.getId(),
                store.getStoreId().getValue(), null,
                "test");

        mockMvc.perform(post("/comments")
                        .content(objectMapper.writeValueAsString(commentRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(jsonPath("commentId").exists())
                // restDocs
                .andDo(document("saveComment",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("commentId").description("댓글 아이디")
                        )

                ))
        ;
    }

    @DisplayName("ProductId 로 Comments 조회")
    @Test
    void findCommentsByProductId() throws Exception {
        // given
        ProductId productId = product.getProductId();
        DomainId storeId = store.getStoreId();
        String productIdValue = productId.getId();
        UserData user = userDao.findByEmail(CONSUMER_EMAIL).orElseThrow(UserNotFoundException::new);
        CommentId commentResponse1 = commentService.create(
                user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), null, "detail"));
        CommentId commentResponse2 = commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), commentResponse1.getId(), "detail"));
        commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), commentResponse2.getId(), "detail"));

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


    @DisplayName("UserId 로 Comments 조회")
    @Test
    void findCommentsByUserId() throws Exception {
        // given
        ProductId productId = product.getProductId();
        DomainId storeId = store.getStoreId();
        userDao.findByEmail(CONSUMER_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        CommentId commentId1 = commentService.create(CONSUMER_EMAIL,
                new CommentRequest(productId.getId(), storeId.getValue(), null, "detail"));
        CommentId commentId2 = commentService.create(CONSUMER_EMAIL,
                new CommentRequest(productId.getId(), storeId.getValue(), commentId1.getId(), "detail"));
        commentService.create(CONSUMER_EMAIL,
                new CommentRequest(productId.getId(), storeId.getValue(), commentId2.getId(), "detail"));

        mockMvc.perform(get("/comments")
                        .param("userId", consumer.getUserId().getId()))
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


    @DisplayName("StoreId 로 Comments 조회")
    @Test
    void findCommentsByStoreId() throws Exception {
        // given
        ProductId productId = product.getProductId();
        DomainId storeId = store.getStoreId();
        UserData user = userDao.findByEmail(CONSUMER_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        CommentId commentId1 = commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), null, "detail"));
        CommentId commentId2 = commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), commentId1.getId(), "detail"));
        commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), commentId2.getId(), "detail"));

        mockMvc.perform(get("/comments")
                        .param("storeId", store.getStoreId().getValue()))
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

    @DisplayName("Comment를 업데이트 한다")
    @Test
    void updateComment() throws Exception {
        // given
        ProductId productId = product.getProductId();
        DomainId storeId = store.getStoreId();
        UserData user = userDao.findByEmail(CONSUMER_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        CommentId commentId = commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), null, "detail"));
        String detail = "Comment를 변경한하다";

        mockMvc.perform(put("/comments/" + commentId.getId())
                        .content(detail)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("commentId").exists());

        Comment comment = commentDao.findById(commentId).orElseThrow();
        assertThat(comment.getDetail()).isEqualTo(detail);
    }

    @DisplayName("Comment를 삭제한다")
    @Test
    void deleteComment() throws Exception {
        // given
        ProductId productId = product.getProductId();
        DomainId storeId = store.getStoreId();
        UserData user = userDao.findByEmail(CONSUMER_EMAIL)
                .orElseThrow(UserNotFoundException::new);
        CommentId commentId = commentService.create(user.getEmail(),
                new CommentRequest(productId.getId(), storeId.getValue(), null, "detail"));

        // when/then
        mockMvc.perform(delete("/comments/" + commentId.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaTypes.HAL_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        Comment comment = commentDao.findById(commentId).orElseThrow();
        assertThat(comment.getDetail()).isBlank();
        assertThat(comment.getUserId()).isNull();
    }
}
