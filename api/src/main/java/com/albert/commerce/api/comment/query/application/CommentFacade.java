package com.albert.commerce.api.comment.query.application;

import com.albert.commerce.api.comment.command.application.CommentNotFoundException;
import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.comment.query.domain.CommentDao;
import com.albert.commerce.api.comment.query.dto.CommentNode;
import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentFacade {


    private final CommentDao commentDao;

    public List<CommentNode> findCommentsResponseByProductId(ProductId productId) {
        List<CommentResponse> commentResponses =
                commentDao.findCommentResponseByProductId(productId);
        return CommentNode.from(commentResponses);
    }

    public void checkId(CommentId parentCommentId) {
        if (parentCommentId != null && !commentDao.exists(parentCommentId)) {
            throw new CommentNotFoundException();
        }
    }

    public List<CommentNode> findCommentsResponseByUserId(UserId userId) {
        List<CommentResponse> commentResponses = commentDao.findCommentResponseByUserId(userId);
        return CommentNode.from(commentResponses);
    }

    public List<CommentNode> findCommentsResponseByStoreId(DomainId storeId) {
        List<CommentResponse> commentResponses = commentDao.findCommentResponseByStoreId(storeId);
        return CommentNode.from(commentResponses);
    }
}
