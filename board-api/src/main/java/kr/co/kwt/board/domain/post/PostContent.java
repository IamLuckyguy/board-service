package kr.co.kwt.board.domain.post;

import kr.co.kwt.board.domain.post.exception.PostPublishException;
import lombok.Value;

@Value
public class PostContent {
    String title;
    String content;

    private PostContent(String title, String content) {
        validate(title, content);
        this.title = title;
        this.content = content;
    }

    public static PostContent of(String title, String content) {
        return new PostContent(title, content);
    }

    private static void validate(String title, String content) {
        if (title == null || title.trim().isEmpty()) {
            throw new PostPublishException("제목을 입력해주세요.");
        }
        if (title.length() > 255) {
            throw new PostPublishException("제목은 255자를 초과할 수 없습니다.");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new PostPublishException("내용을 입력해주세요.");
        }
    }
}