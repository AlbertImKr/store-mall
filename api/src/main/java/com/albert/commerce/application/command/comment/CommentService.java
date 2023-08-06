package com.albert.commerce.application.command.comment;

import com.albert.commerce.application.query.comment.CommentFacade;
import com.albert.commerce.application.query.product.ProductFacade;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.comment.Comment;
import com.albert.commerce.domain.command.comment.CommentId;
import com.albert.commerce.domain.command.comment.CommentRepository;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.query.store.StoreDataDao;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
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
