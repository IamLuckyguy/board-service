package kr.co.kwt.board.application.service;

import kr.co.kwt.board.application.port.out.comment.LoadCommentPort;
import kr.co.kwt.board.application.port.in.like.ToggleLikeUseCase;
import kr.co.kwt.board.application.port.out.like.LoadLikePort;
import kr.co.kwt.board.application.port.out.like.SaveLikePort;
import kr.co.kwt.board.application.port.out.post.LoadPostPort;
import kr.co.kwt.board.domain.comment.Comment;
import kr.co.kwt.board.domain.comment.exception.CommentNotFoundException;
import kr.co.kwt.board.domain.like.Like;
import kr.co.kwt.board.domain.like.LikeType;
import kr.co.kwt.board.domain.post.Post;
import kr.co.kwt.board.domain.post.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService implements ToggleLikeUseCase {
    private final LoadLikePort loadLikePort;
    private final SaveLikePort saveLikePort;
    private final LoadPostPort loadPostPort;
    private final LoadCommentPort loadCommentPort;

    @Override
    @Transactional
    public boolean toggleLike(ToggleLikeCommand command) {
        // 이미 좋아요를 눌렀는지 확인
        Optional<Like> existingLike = loadLikePort.findByTargetIdAndUserIdAndType(
                command.getTargetId(),
                command.getUserId(),
                command.getType()
        );

        if (existingLike.isPresent()) {
            // 좋아요가 이미 있으면 취소
            saveLikePort.delete(existingLike.get());
            updateLikeCount(command.getTargetId(), command.getType());
            return false;
        } else {
            // 새로운 좋아요 생성
            Like newLike = Like.builder()
                    .targetId(command.getTargetId())
                    .userId(command.getUserId())
                    .type(command.getType())
                    .build();
            saveLikePort.save(newLike);
            updateLikeCount(command.getTargetId(), command.getType());
            return true;
        }
    }

    private void updateLikeCount(Long targetId, LikeType type) {
        long likeCount = loadLikePort.countByTargetIdAndType(targetId, type);

        if (type == LikeType.POST) {
            Post post = loadPostPort.findById(targetId)
                    .orElseThrow(() -> new PostNotFoundException(targetId));
            post.updateLikeCount((int) likeCount);
        } else {
            Comment comment = loadCommentPort.findById(targetId)
                    .orElseThrow(() -> new CommentNotFoundException(targetId));
            comment.updateLikeCount((int) likeCount);
        }
    }
}