package com.albert.commerce.infra.command.comment.persistence;

import com.albert.commerce.application.command.comment.CommentResponse;
import com.albert.commerce.comment.infra.persistence.imports.CommentJpaRepository;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.domain.command.comment.Comment;
import com.albert.commerce.domain.command.comment.CommentId;
import com.albert.commerce.domain.command.comment.CommentRepository;
import com.albert.commerce.domain.command.comment.QComment;
import com.albert.commerce.domain.command.product.ProductId;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.QUser;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.comment.CommentDao;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository, CommentDao {

    private final CommentJpaRepository commentJpaRepository;
    private final SequenceGenerator sequenceGenerator;
    private final JPAQueryFactory jpaQueryFactory;

    private CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

    @Override
    public Comment save(Comment comment) {
        comment.updateId(nextId());
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId);
    }

    @Override
    public List<CommentResponse> findCommentResponseByProductId(ProductId productId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.fields(CommentResponse.class,
                        comment.commentId,
                        comment.userId,
                        comment.storeId,
                        comment.productId,
                        comment.detail,
                        comment.parentCommentId,
                        comment.createdTime,
                        comment.updateTime,
                        user.nickname
                ))
                .from(comment)
                .join(user).on(comment.userId.eq(user.userId))
                .where(comment.productId.eq(productId))
                .fetch();
    }

    @Override
    public List<CommentResponse> findCommentResponseByUserId(UserId userId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.fields(CommentResponse.class,
                        comment.commentId,
                        comment.userId,
                        comment.storeId,
                        comment.productId,
                        comment.detail,
                        comment.parentCommentId,
                        comment.createdTime,
                        comment.updateTime,
                        user.nickname
                ))
                .from(comment)
                .join(user).on(comment.userId.eq(user.userId))
                .where(comment.userId.eq(userId))
                .fetch();
    }

    @Override
    public List<CommentResponse> findCommentResponseByStoreId(StoreId storeId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.fields(CommentResponse.class,
                        comment.commentId,
                        comment.userId,
                        comment.storeId,
                        comment.productId,
                        comment.detail,
                        comment.parentCommentId,
                        comment.createdTime,
                        comment.updateTime,
                        user.nickname
                ))
                .from(comment)
                .join(user).on(comment.userId.eq(user.userId))
                .where(comment.storeId.eq(storeId))
                .fetch();
    }

    @Override
    public boolean exists(CommentId commentId) {
        return commentJpaRepository.existsById(commentId);
    }

}
