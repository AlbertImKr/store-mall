package com.albert.commerce.api.comment.infra.persistence.imports;

import com.albert.commerce.api.comment.command.domain.Comment;
import com.albert.commerce.common.domain.DomainId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<Comment, DomainId> {

}
