package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.product.command.application.ProductService;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
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
    public DomainId create(String userEmail, CommentRequest commentRequest) {
        DomainId userId = userService.findIdByEmail(userEmail);

        DomainId productId = DomainId.from(commentRequest.productId());
        productService.checkId(productId);

        DomainId storeId = DomainId.from(commentRequest.storeId());
        storeService.checkId(storeId);

        DomainId parentCommentId = getParentCommentId(commentRequest.parentCommentId());
        Comment comment = toComment(commentRequest, userId, productId, storeId, parentCommentId);
        return commentRepository.save(comment).getCommentId();
    }

    private DomainId getParentCommentId(String parentCommentIdValue) {
        DomainId parentCommentId = parentCommentIdValue != null ?
                DomainId.from(parentCommentIdValue) :
                null;
        if (parentCommentId != null && !commentRepository.existsById(parentCommentId)) {
            throw new CommentNotFoundException();
        }
        return parentCommentId;
    }

    private static Comment toComment(CommentRequest commentRequest, DomainId userId, DomainId productId,
            DomainId storeId, DomainId parentCommentId) {
        return Comment.builder()
                .productId(productId)
                .storeId(storeId)
                .userId(userId)
                .detail(commentRequest.detail())
                .parentCommentId(parentCommentId)
                .build();
    }

    @Transactional
    public DomainId update(DomainId commentId, String detail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(detail, LocalDateTime.now());
        return comment.getCommentId();
    }

    @Transactional
    public void delete(DomainId commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete();
    }
}
