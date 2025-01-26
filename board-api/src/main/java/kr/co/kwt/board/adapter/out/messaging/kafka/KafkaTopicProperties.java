package kr.co.kwt.board.adapter.out.messaging.kafka;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.kafka.topic")
@Getter
@Setter
public class KafkaTopicProperties {
    private Post post;
    private Comment comment;
    private Like like;

    @Getter
    @Setter
    public static class Post {
        private String created;
        private String updated;
        private String deleted;
    }

    @Getter
    @Setter
    public static class Comment {
        private String created;
    }

    @Getter
    @Setter
    public static class Like {
        private String added;
    }
}