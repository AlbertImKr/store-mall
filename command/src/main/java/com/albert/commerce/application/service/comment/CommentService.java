package com.albert.commerce.application.service.comment;

import com.albert.commerce.application.port.out.CommentRepository;
import com.albert.commerce.application.service.exception.error.CommentNotFoundException;
import com.albert.commerce.application.service.product.ProductService;
import com.albert.commerce.application.service.store.StoreService;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
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
        productService.checkProductExist(productId);

        var storeId = StoreId.from(commentPostCommand.getStoreId());
        storeService.checkExist(storeId);

        var parentCommentId = getParentCommentId(commentPostCommand.getParentCommentId());
        var comment = Comment.from(
                getNewCommentId(),
                productId,
                storeId,
                userId,
                commentPostCommand.getDetail(),
                parentCommentId,
                LocalDateTime.now()
        );
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
    public Success delete(CommentDeleteCommand commentDeleteCommand) {
        var userId = userService.getUserIdByEmail(commentDeleteCommand.getUserEmail());

        var commentId = CommentId.from(commentDeleteCommand.getCommandId());
        var comment = commentRepository.findByCommentIdAndUserId(commentId, userId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete(LocalDateTime.now());
        return Success.getInstance();
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

    private CommentId getNewCommentId() {
        return commentRepository.nextId();
    }
}
