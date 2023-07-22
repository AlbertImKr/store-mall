package com.albert.commerce.comment.command.application;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.query.application.CommentFacade;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserDao userDao;
    private final ProductFacade productFacade;
    private final StoreDataDao storeDataDao;
    private final CommentFacade commentFacade;

    @Transactional
    public CommentId create(String userEmail, CommentRequest commentRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        ProductId productId = ProductId.from(commentRequest.productId());
        productFacade.checkId(productId);

        StoreId storeId = StoreId.from(commentRequest.storeId());
        if (!storeDataDao.exists(storeId)) {
            throw new StoreNotFoundException();
        }

        CommentId parentCommentId = commentRequest.parentCommentId() != null ?
                CommentId.from(commentRequest.parentCommentId()) :
                null;
        commentFacade.checkId(parentCommentId);
        Comment comment = Comment.builder()
                .productId(productId)
                .storeId(storeId)
                .userId(user.getUserId())
                .detail(commentRequest.detail())
                .parentCommentId(parentCommentId)
                .build();
        return commentRepository.save(comment).getCommentId();
    }

    @Transactional
    public CommentId update(CommentId commentId, String detail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(detail);
        return comment.getCommentId();
    }

    @Transactional
    public void delete(CommentId commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete();
    }
}
