package com.albert.commerce.application.query.comment;

import com.albert.commerce.application.command.comment.CommentNotFoundException;
import com.albert.commerce.application.command.comment.CommentResponse;
import com.albert.commerce.application.query.comment.dto.CommentNode;
import com.albert.commerce.domain.command.comment.CommentId;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.comment.CommentDao;
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
