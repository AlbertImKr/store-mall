package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, CommentId> {

}
