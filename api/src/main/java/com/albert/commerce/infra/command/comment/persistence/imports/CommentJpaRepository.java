package com.albert.commerce.comment.infra.persistence.imports;

import com.albert.commerce.domain.command.comment.Comment;
import com.albert.commerce.domain.command.comment.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

}
