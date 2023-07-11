package com.albert.commerce.comment.query.application;

import com.albert.commerce.comment.command.application.CommentNotFoundException;
import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.query.domain.CommentDao;
import com.albert.commerce.comment.query.dto.CommentNode;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
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

    public List<CommentNode> findCommentsResponseByStoreId(StoreId storeId) {
        List<CommentResponse> commentResponses = commentDao.findCommentResponseByStoreId(storeId);
        return CommentNode.from(commentResponses);
    }
}
