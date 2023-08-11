package com.albert.commerce.api.comment.query.application;

import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.query.application.dto.CommentCreatedRequest;
import com.albert.commerce.api.comment.query.application.dto.CommentNode;
import com.albert.commerce.api.comment.query.domain.CommentDao;
import com.albert.commerce.api.comment.query.domain.CommentData;
import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.user.query.application.UserFacade;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CommentFacade {

    private final CommentDao commentDao;
    private final ProductFacade productFacade;
    private final StoreFacade storeFacade;
    private final UserFacade userFacade;

    public List<CommentNode> findCommentsResponseByProductId(DomainId productId) {
        List<CommentResponse> commentResponses =
                commentDao.findCommentResponseByProductId(productId);
        return CommentNode.from(commentResponses);
    }

    public List<CommentNode> findCommentsResponseByUserId(DomainId userId) {
        List<CommentResponse> commentResponses = commentDao.findCommentResponseByUserId(userId);
        return CommentNode.from(commentResponses);
    }

    public List<CommentNode> findCommentsResponseByStoreId(DomainId storeId) {
        List<CommentResponse> commentResponses = commentDao.findCommentResponseByStoreId(storeId);
        return CommentNode.from(commentResponses);
    }

    public CommentData create(CommentCreatedRequest commentCreatedRequest) {
        ProductResponse product = productFacade.getProductId(commentCreatedRequest.getProductId());
        UserData user = userFacade.getUserById(commentCreatedRequest.getUserId());
        StoreData store = storeFacade.getStoreById(commentCreatedRequest.getStoreId());
        CommentData comment = toComment(commentCreatedRequest, product, user, store);
        return commentDao.save(comment);
    }

    private static CommentData toComment(CommentCreatedRequest commentCreatedRequest, ProductResponse product,
            UserData user, StoreData store) {
        return CommentData.builder()
                .commentId(commentCreatedRequest.getCommentId())
                .productId(commentCreatedRequest.getProductId())
                .productName(product.getProductName())
                .userId(commentCreatedRequest.getUserId())
                .nickname(user.getNickname())
                .storeId(commentCreatedRequest.getStoreId())
                .storeName(store.getStoreName())
                .detail(commentCreatedRequest.getDetail())
                .parentCommentId(commentCreatedRequest.getParentCommentId())
                .createdTime(commentCreatedRequest.getCreatedTime())
                .updatedTime(commentCreatedRequest.getUpdateTime())
                .build();
    }
}
