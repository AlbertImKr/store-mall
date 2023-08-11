package com.albert.commerce.api.comment.infra.persistence;

import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.api.comment.command.domain.CommentRepository;
import com.albert.commerce.api.comment.command.domain.QComment;
import com.albert.commerce.api.comment.infra.persistence.imports.CommentJpaRepository;
import com.albert.commerce.api.comment.query.domain.CommentDao;
import com.albert.commerce.api.user.command.domain.QUser;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
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

    private DomainId nextId() {
        return DomainId.from(sequenceGenerator.generate());
    }

    @Override
    public Comment save(Comment comment) {
        comment.updateId(nextId());
        return commentJpaRepository.save(comment);
    }

    @Override
    public Optional<Comment> findById(DomainId commentId) {
        return commentJpaRepository.findById(commentId);
    }

    @Override
    public List<CommentResponse> findCommentResponseByProductId(DomainId productId) {
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
    public List<CommentResponse> findCommentResponseByUserId(DomainId userId) {
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
    public List<CommentResponse> findCommentResponseByStoreId(DomainId storeId) {
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
    public boolean exists(DomainId commentId) {
        return commentJpaRepository.existsById(commentId);
    }

}