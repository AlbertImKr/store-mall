package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.CommentRepository;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.CommentNotFoundException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ProductService productService;
    private final StoreService storeService;

    @Transactional
    @ServiceActivator(inputChannel = "CommentPostCommand")
    public String post(CommentPostCommand commentPostCommand) {
        var userId = userService.getUserIdByEmail(commentPostCommand.getUserEmail());

        var productId = ProductId.from(commentPostCommand.getProductId());
        productService.checkId(productId);

        var storeId = StoreId.from(commentPostCommand.getStoreId());
        storeService.checkId(storeId);

        var parentCommentId = getParentCommentId(commentPostCommand.getParentCommentId());
        var comment = toComment(productId, storeId, userId, commentPostCommand.getDetail(), parentCommentId);
        return commentRepository.save(comment)
                .getCommentId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "CommentUpdateCommand")
    public String update(CommentUpdateCommand commentUpdateCommand) {
        var userId = userService.getUserIdByEmail(commentUpdateCommand.getUserEmail());

        var commentId = CommentId.from(commentUpdateCommand.getCommandId());
        var comment = commentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(commentUpdateCommand.getDetail(), LocalDateTime.now());
        return comment.getCommentId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "CommentDeleteCommand")
    public void delete(CommentDeleteCommand commentDeleteCommand) {
        var userId = userService.getUserIdByEmail(commentDeleteCommand.getUserEmail());

        var commentId = CommentId.from(commentDeleteCommand.getCommandId());
        var comment = commentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(LocalDateTime.now());
    }

    private CommentId getParentCommentId(String parentCommentIdValue) {
        var parentCommentId = parentCommentIdValue != null ?
                CommentId.from(parentCommentIdValue) :
                null;
        if (parentCommentId != null && !commentRepository.existsById(parentCommentId)) {
            throw new CommentNotFoundException();
        }
        return parentCommentId;
    }

    private static Comment toComment(ProductId productId, StoreId storeId, UserId userId, String detail,
            CommentId parentCommentId) {
        return Comment.builder()
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(detail)
                .parentCommentId(parentCommentId)
                .build();
    }
}
