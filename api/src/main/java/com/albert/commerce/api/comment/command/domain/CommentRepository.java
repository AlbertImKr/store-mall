package com.albert.commerce.api.comment.command.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    boolean existsById(DomainId domainId);

    Optional<Comment> findById(DomainId commentId);
}
