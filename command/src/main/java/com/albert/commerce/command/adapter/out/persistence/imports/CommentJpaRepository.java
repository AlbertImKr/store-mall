package com.albert.commerce.command.adapter.out.persistence.imports;

import com.albert.commerce.command.domain.comment.Comment;
import com.albert.commerce.command.domain.comment.CommentId;
import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

    Optional<Comment> findByCommentIdAndUserId(CommentId commentId, UserId userId);
}
