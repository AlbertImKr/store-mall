package com.albert.commerce.comment.query;

import com.albert.commerce.comment.command.application.CommentResponse;
import com.albert.commerce.comment.command.domain.CommentId;
import com.albert.commerce.product.command.domain.ProductId;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentDaoService {

    private final CommentDao commentDao;

    public List<CommentDTO> findCommentResponseByProductId(ProductId productId) {
        List<CommentResponse> commentResponseByProductId =
                commentDao.findCommentResponseByProductId(productId);
        List<CommentDTO> rootComments = commentResponseByProductId.stream()
                .filter(commentResponse -> commentResponse.getChildCommentId() == null)
                .map(CommentDTO::from)
                .toList();
        Map<String, CommentDTO> childComments = commentResponseByProductId.stream()
                .filter(commentResponse -> commentResponse.getChildCommentId() != null)
                .collect(Collectors.toMap(
                        commentResponse -> commentResponse.getCommentId().getId(),
                        CommentDTO::from));
        for (CommentDTO rootComment : rootComments) {
            CommentDTO currentComment = rootComment;
            CommentId childCommentId = currentComment.getChildCommentId();
            while (childCommentId != null) {
                CommentDTO commentDTO = childComments.get(childCommentId);
                currentComment.setChildComment(commentDTO);
                childCommentId = commentDTO.getChildCommentId();
                currentComment = commentDTO;
            }
        }

        return rootComments;
    }

}
