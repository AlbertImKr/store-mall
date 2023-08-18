package com.albert.commerce.query.infra.persistance.imports;

import com.albert.commerce.query.domain.comment.CommentData;
import com.albert.commerce.query.domain.comment.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentDataJpaRepository extends JpaRepository<CommentData, CommentId> {

}
