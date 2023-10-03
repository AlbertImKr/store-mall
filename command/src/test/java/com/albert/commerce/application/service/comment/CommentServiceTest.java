package com.albert.commerce.application.service.comment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.albert.commerce.application.port.out.CommentRepository;
import com.albert.commerce.application.service.ApplicationFixture;
import com.albert.commerce.application.service.exception.error.CommentNotFoundException;
import com.albert.commerce.domain.comment.CommentId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @InjectMocks
    CommentService commentService;
    @Mock
    CommentRepository commentRepository;

    @DisplayName("댓글 아이디와 유저 아이디로 댓글 조회 시 존재하지 않으면 예외를 던진다")
    @Test
    void when_comment_not_found_then_throw_exception() {
        // given
        UserId userId = ApplicationFixture.getUserId();
        CommentId commentId = ApplicationFixture.getCommentId();
        given(commentRepository.findByCommentIdAndUserId(commentId, userId)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> commentService.getCommentByIdAndUserId(commentId, userId))
                .isInstanceOf(CommentNotFoundException.class);
    }

    @DisplayName("댓글 아이디 체크 시 존재하지 않으면 예외를 던진다")
    @Test
    void checkCommentId() {
        // given
        CommentId commentId = ApplicationFixture.getCommentId();
        given(commentRepository.existsById(commentId)).willReturn(false);

        // when
        assertThatThrownBy(() -> commentService.checkCommentId(commentId))
                .isInstanceOf(CommentNotFoundException.class);
    }
}
