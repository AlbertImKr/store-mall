package com.albert.commerce.comment.ui;

//import com.albert.commerce.comment.command.apllication.CommentRequest;
//import com.albert.commerce.comment.command.apllication.CommentResponse;
//import com.albert.commerce.comment.command.apllication.CommentService;

import com.albert.commerce.comment.command.apllication.CommentRequest;
import com.albert.commerce.comment.command.apllication.CommentResponse;
import com.albert.commerce.comment.command.apllication.CommentService;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.query.CommentDTO;
import com.albert.commerce.comment.query.CommentDaoService;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/comments", produces = MediaTypes.HAL_JSON_VALUE)
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;
    private final CommentDaoService commentDaoService;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final StoreDao storeDao;


    @PostMapping
    public EntityModel<CommentResponse> saveComment(@RequestBody CommentRequest commentRequest,
            Principal principal) {
        User user = userDao.findUserProfileByEmail(principal.getName());
        Product product = productDao.findById(commentRequest.productId());
        Store store = storeDao.findById(commentRequest.storeId());
        CommentId parentCommentId = commentRequest.parentCommentId();
        if (parentCommentId == null) {
            return EntityModel.of(
                    commentService.save(user.getId(), user.getNickname(), product.getProductId(),
                            store.getStoreId(),
                            commentRequest.detail()));
        }
        return EntityModel.of(
                commentService.save(user.getId(), user.getNickname(), product.getProductId(),
                        store.getStoreId(),
                        commentRequest.detail(), parentCommentId));
    }

    @GetMapping(params = "productId")
    public CollectionModel<CommentDTO> findCommentsByProductId(ProductId productId) {
        return CollectionModel.of(commentDaoService.findCommentResponseByProductId(productId));
    }
}
