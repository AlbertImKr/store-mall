package com.albert.commerce.api.comment.query.dto;

import com.albert.commerce.api.comment.command.application.CommentResponse;
import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.common.domain.DomainId;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

@Builder
@Getter
@Setter
@Relation(value = "comment", itemRelation = "comment", collectionRelation = "comments")
public class CommentNode {

    private CommentId commentId;
    private DomainId userId;
    private String nickname;
    private DomainId storeId;
    private ProductId productId;
    private LocalDateTime createdTime;
    private LocalDateTime updateTime;
    private CommentId parentCommentId;
    private String detail;

    @JsonProperty(value = "comment")
    private CommentNode childCommentNode;

    public static List<CommentNode> from(List<CommentResponse> commentResponses) {
        List<CommentNode> seedCommentNodes = getSeedCommentNodes(commentResponses);
        addChildToSeed(seedCommentNodes, getChildCommentNodesEntry(commentResponses));
        return seedCommentNodes;
    }

    private static void addChildToSeed(List<CommentNode> seedCommentNodes,
            Map<CommentId, CommentNode> childCommentNodes) {
        for (CommentNode seedComment : seedCommentNodes) {
            CommentNode currentComment = seedComment;
            while (childCommentNodes.containsKey(currentComment.getCommentId())) {
                CommentNode childCommentNode = childCommentNodes.get(currentComment.getCommentId());
                currentComment.updateChild(childCommentNode);
                currentComment = childCommentNode;
            }
        }
    }

    private static Map<CommentId, CommentNode> getChildCommentNodesEntry(
            List<CommentResponse> commentResponses) {
        return commentResponses.stream()
                .filter(commentResponse -> commentResponse.getParentCommentId() != null)
                .collect(Collectors.toMap(
                        CommentResponse::getParentCommentId,
                        CommentNode::from));
    }

    private static List<CommentNode> getSeedCommentNodes(
            List<CommentResponse> commentResponseByProductId) {
        return commentResponseByProductId.stream()
                .filter(commentResponse -> commentResponse.getParentCommentId() == null)
                .map(CommentNode::from)
                .toList();
    }

    public static CommentNode from(CommentResponse commentResponse) {
        return CommentNode.builder()
                .commentId(commentResponse.getCommentId())
                .storeId(commentResponse.getStoreId())
                .productId(commentResponse.getProductId())
                .createdTime(commentResponse.getCreatedTime())
                .updateTime(commentResponse.getUpdateTime())
                .nickname(commentResponse.getNickname())
                .parentCommentId(commentResponse.getParentCommentId())
                .detail(commentResponse.getDetail())
                .userId(commentResponse.getUserId())
                .build();
    }

    public void updateChild(CommentNode commentNode) {
        this.childCommentNode = commentNode;
    }
}
