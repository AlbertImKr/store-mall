package com.albert.commerce.query.infra.persistance;

import com.albert.commerce.query.domain.comment.CommentDao;
import com.albert.commerce.query.domain.comment.CommentData;
import com.albert.commerce.query.domain.comment.CommentId;
import com.albert.commerce.query.infra.persistance.imports.CommentDataJpaRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {

    private final CommentDataJpaRepository commentDataJpaRepository;

    @Override
    public Optional<CommentData> findById(CommentId commentId) {
        return commentDataJpaRepository.findById(commentId);
    }

    @Override
    public boolean exists(CommentId commentId) {
        return false;
    }

    @Override
    public CommentData save(CommentData comment) {
        return commentDataJpaRepository.save(comment);
    }
}
