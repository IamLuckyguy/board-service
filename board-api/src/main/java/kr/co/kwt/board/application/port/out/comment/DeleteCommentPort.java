package kr.co.kwt.board.application.port.out.comment;

public interface DeleteCommentPort {
    void delete(Long commentId);

    void deleteByPostId(Long postId);
}