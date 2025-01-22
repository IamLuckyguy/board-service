package kr.co.kwt.board.domain.post;

import lombok.Value;

@Value
public class PostContent {
    String title;
    String content;

    private PostContent(String title, String content) {
        validateTitle(title);
        validateContent(content);
        this.title = title;
        this.content = content;
    }

    public static PostContent of(String title, String content) {
        return new PostContent(title, content);
    }

    private void validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if (title.length() > 255) {
            throw new IllegalArgumentException("Title cannot be longer than 255 characters");
        }
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be empty");
        }
    }
}