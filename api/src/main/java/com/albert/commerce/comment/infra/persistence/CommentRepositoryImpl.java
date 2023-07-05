package com.albert.commerce.comment.infra.persistence;

import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.command.domain.QComment;
import com.albert.commerce.comment.infra.persistence.imports.CommentJpaRepository;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryImpl implements CommentRepository, CommentDao {

    private final CommentJpaRepository commentJpaRepository;
    private final SequenceGenerator sequenceGenerator;
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public CommentId nextId() {
        return CommentId.from(sequenceGenerator.generate());
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(comment);
    }

    @Override
    public Comment findById(CommentId commentId) {
        return commentJpaRepository.findById(commentId).orElseThrow();
    }

    @Override
    public List<CommentResponse> findCommentResponseByProductId(ProductId productId) {
        QComment comment = QComment.comment;
        QUser user = QUser.user;

        return jpaQueryFactory.select(Projections.bean(CommentResponse.class,
                        comment.commentId,
                        comment.userId,
                        comment.storeId,
                        comment.productId,
                        comment.detail,
                        comment.childCommentId,
                        comment.createdTime,
                        comment.updateTime,
                        user.nickname
                ))
                .from(comment)
                .join(user).on(comment.userId.eq(user.id))
                .where(comment.productId.eq(productId))
                .fetch();
    }

}
