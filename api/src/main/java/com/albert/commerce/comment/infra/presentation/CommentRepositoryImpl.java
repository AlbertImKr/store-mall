package com.albert.commerce.comment.infra.presentation;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.comment.command.domain.CommentRepository;
import com.albert.commerce.comment.command.domain.QComment;
import com.albert.commerce.comment.infra.presentation.imports.CommentJpaRepository;
import com.albert.commerce.comment.query.CommentDao;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.ProductId;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private static void linkedDTO(Map<CommentId, CommentDTO> collect,
            Map<CommentId, CommentDTO> allCommentDTOS) {
        while (!allCommentDTOS.isEmpty()) {
            List<CommentId> commentIds = collect.keySet().stream().toList();
            for (CommentId commentId : commentIds) {
                CommentDTO childComment = allCommentDTOS.get(commentId);
                if (childComment == null) {
                    continue;
                }
                allCommentDTOS.remove(commentId);
                CommentDTO parentComment = collect.get(commentId);
                CommentDTO leafComment = parentComment;
                while (leafComment.getChildCommentDTO() != null) {
                    leafComment = leafComment.getChildCommentDTO();
                }
                leafComment.updateChildCommentDTO(childComment);
                collect.remove(commentId);
                collect.put(childComment.getCommentId(), parentComment);
            }
        }
    }

    @Override
    public Comment findById(CommentId commentId) {
        QComment qComment = QComment.comment;
        Tuple commentTuple = jpaQueryFactory
                .select(qComment.commentId,
                        qComment.productId,
                        qComment.userId,
                        qComment.nickName,
                        qComment.storeId,
                        qComment.detail,
                        qComment.createdTime,
                        qComment.updateTime)
                .from(qComment)
                .where(qComment.commentId.eq(commentId))
                .fetchFirst();
        CommentDTO commentDTO = toCommentDTO(commentTuple, qComment);
        return commentDTO.toEntity();
    }

    @Override
    public Collection<CommentDTO> findByProductId(ProductId productId) {
        QComment qComment = QComment.comment;

        List<Tuple> rootComment = jpaQueryFactory
                .select(qComment.commentId,
                        qComment.productId,
                        qComment.userId,
                        qComment.nickName,
                        qComment.storeId,
                        qComment.detail,
                        qComment.createdTime,
                        qComment.updateTime)
                .from(qComment)
                .where(qComment.productId.eq(productId).and(qComment.parentComment.isNull()))
                .fetch();

        List<Tuple> childComments = jpaQueryFactory.select(
                        qComment.commentId,
                        qComment.productId,
                        qComment.userId,
                        qComment.nickName,
                        qComment.storeId,
                        qComment.detail,
                        qComment.createdTime,
                        qComment.updateTime,
                        qComment.parentComment.commentId
                )
                .from(qComment)
                .where(qComment.productId.eq(productId).and(qComment.parentComment.isNotNull()))
                .fetch();

        Map<CommentId, CommentDTO> collect = rootComment.stream()
                .map(tuple -> toCommentDTO(tuple, qComment))
                .collect(Collectors.toMap(CommentDTO::getCommentId, commentDTO -> commentDTO));

        Map<CommentId, CommentDTO> allCommentDTOS = childComments.stream()
                .map(tuple -> toCommentDTO(tuple, qComment))
                .collect(
                        Collectors.toMap(CommentDTO::getParentCommentId, commentDTO -> commentDTO));
        linkedDTO(collect, allCommentDTOS);

        return collect.values();
    }

    private CommentDTO toCommentDTO(Tuple tuple, QComment comment) {
        return CommentDTO.builder()
                .commentId(tuple.get(comment.commentId))
                .parentCommentId(tuple.get(comment.parentComment.commentId))
                .userId(tuple.get(comment.userId))
                .nickName(tuple.get(comment.nickName))
                .storeId(tuple.get(comment.storeId))
                .detail(tuple.get(comment.detail))
                .createdTime(tuple.get(comment.createdTime))
                .updateTime(tuple.get(comment.updateTime))
                .build();
    }

}
