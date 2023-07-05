package com.albert.commerce.comment.query.application;

import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.query.domain.CommentDao;
import com.albert.commerce.comment.query.dto.CommentNode;
import com.albert.commerce.product.command.domain.ProductId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentDaoFacade {


    private final CommentDao commentDao;

    public List<CommentNode> findCommentsResponseByProductId(String productId) {
        List<CommentResponse> commentResponses =
                commentDao.findCommentResponseByProductId(ProductId.from(productId));
        return CommentNode.from(commentResponses);
    }

}
