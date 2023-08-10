package com.albert.commerce.api.comment.command.application;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.comment.query.application.CommentFacade;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.exception.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserDao userDao;
    private final ProductFacade productFacade;
    private final StoreService storeService;
    private final CommentFacade commentFacade;

    @Transactional
    public DomainId create(String userEmail, CommentRequest commentRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);

        DomainId productId = DomainId.from(commentRequest.productId());
        productFacade.checkId(productId);

        DomainId storeId = DomainId.from(commentRequest.storeId());
        if (!storeService.exists(storeId)) {
            throw new StoreNotFoundException();
        }

        DomainId parentCommentId = commentRequest.parentCommentId() != null ?
                DomainId.from(commentRequest.parentCommentId()) :
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
    public DomainId update(DomainId commentId, String detail) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.update(detail);
        return comment.getCommentId();
    }

    @Transactional
    public void delete(DomainId commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);
        comment.delete();
    }
}
