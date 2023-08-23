package com.albert.commerce.adapter.out.persistence.imports;

import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

    Optional<Comment> findByCommentIdAndUserId(CommentId commentId, UserId userId);
}
