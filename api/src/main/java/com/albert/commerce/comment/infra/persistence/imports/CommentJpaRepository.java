package com.albert.commerce.comment.infra.persistence.imports;

import com.albert.commerce.comment.command.domain.Comment;
import com.albert.commerce.comment.command.domain.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

}
