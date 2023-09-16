package com.albert.commerce.adapter.in.web.facade;

import com.albert.commerce.application.port.out.CommentDao;
import com.albert.commerce.config.cache.CacheValue;
import com.albert.commerce.domain.comment.Comment;
import com.albert.commerce.domain.product.ProductId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentFacade {

    private final CommentDao commentDao;

    @Cacheable(value = CacheValue.COMMENT)
    public List<Comment> getAllByProductId(String productId) {
        return commentDao.findAllByProductId(ProductId.from(productId));
    }
}
