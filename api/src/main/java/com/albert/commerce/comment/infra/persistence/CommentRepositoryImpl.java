package com.albert.commerce.comment.infra.persistence;

import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.command.domain.QComment;
import com.albert.commerce.comment.infra.persistence.imports.CommentJpaRepository;
import com.albert.commerce.comment.query.domain.CommentDao;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.user.command.domain.QUser;
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
    public boolean exists(CommentId commentId) {
        return commentJpaRepository.existsById(commentId);
    }
}
