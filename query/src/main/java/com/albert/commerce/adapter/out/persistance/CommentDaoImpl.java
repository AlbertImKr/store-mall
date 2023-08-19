package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.CommentDataJpaRepository;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.comment.CommentDao;
import com.albert.commerce.domain.comment.CommentId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CommentDaoImpl implements CommentDao {

    private final CommentDataJpaRepository commentDataJpaRepository;

    @Override
    public Optional<Comment> findById(CommentId commentId) {
        return commentDataJpaRepository.findById(commentId);
    }

    @Override
    public boolean exists(CommentId commentId) {
        return false;
    }

    @Override
    public Comment save(Comment comment) {
        return commentDataJpaRepository.save(comment);
    }
}
