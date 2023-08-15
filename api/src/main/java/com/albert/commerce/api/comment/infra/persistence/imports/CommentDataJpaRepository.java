package com.albert.commerce.api.comment.infra.persistence.imports;

import com.albert.commerce.api.comment.command.domain.CommentId;
import com.albert.commerce.api.comment.query.domain.CommentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDataJpaRepository extends JpaRepository<CommentData, CommentId> {

}
