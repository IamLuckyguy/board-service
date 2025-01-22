package kr.co.kwt.board.adapter.out.persistence.like;

import jakarta.persistence.*;
import kr.co.kwt.board.domain.like.LikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "likes")
@Getter
@NoArgsConstructor
public class LikeJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "target_id")
    private Long targetId;

    @Column(name = "user_id")
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private LikeType type;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public LikeJpaEntity(Long id, Long targetId, Long userId,
                         LikeType type, LocalDateTime createdAt) {
        this.id = id;
        this.targetId = targetId;
        this.userId = userId;
        this.type = type;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }
}