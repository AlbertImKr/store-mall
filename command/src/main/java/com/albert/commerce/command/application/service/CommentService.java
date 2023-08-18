package com.albert.commerce.command.application.service;

import com.albert.commerce.command.application.port.out.CommentRepository;
import com.albert.commerce.command.domain.comment.Comment;
import com.albert.commerce.command.domain.comment.CommentId;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.exception.CommentNotFoundException;
import com.albert.commerce.shared.messaging.application.CommentCreateCommand;
import com.albert.commerce.shared.messaging.application.CommentDeleteCommand;
import com.albert.commerce.shared.messaging.application.CommentUpdateCommand;
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
    @ServiceActivator(inputChannel = "CommentCreateCommand")
    public String create(CommentCreateCommand commentCreateCommand) {
        UserId userId = userService.getUserIdByEmail(commentCreateCommand.getUserEmail());

        ProductId productId = ProductId.from(commentCreateCommand.getProductId());
        productService.checkId(productId);

        StoreId storeId = StoreId.from(commentCreateCommand.getStoreId());
        storeService.checkId(storeId);

        CommentId parentCommentId = getParentCommentId(commentCreateCommand.getParentCommentId());
        Comment comment = toComment(productId, storeId, userId, commentCreateCommand.getDetail(), parentCommentId);
        return commentRepository.save(comment)
                .getCommentId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "CommentUpdateCommand")
    public String update(CommentUpdateCommand commentUpdateCommand) {
        UserId userId = userService.getUserIdByEmail(commentUpdateCommand.getUserEmail());

        CommentId commentId = CommentId.from(commentUpdateCommand.getCommandId());
        Comment comment = commentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(commentUpdateCommand.getDetail(), LocalDateTime.now());
        return comment.getCommentId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "CommentDeleteCommand")
    public void delete(CommentDeleteCommand commentDeleteCommand) {
        UserId userId = userService.getUserIdByEmail(commentDeleteCommand.getUserEmail());

        CommentId commentId = CommentId.from(commentDeleteCommand.getCommandId());
        Comment comment = commentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(LocalDateTime.now());
    }

    private CommentId getParentCommentId(String parentCommentIdValue) {
        CommentId parentCommentId = parentCommentIdValue != null ?
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
