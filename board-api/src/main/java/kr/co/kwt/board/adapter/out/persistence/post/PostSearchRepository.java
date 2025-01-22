package kr.co.kwt.board.adapter.out.persistence.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostSearchRepository extends JpaRepository<PostJpaEntity, Long> {
    @Query(value = "SELECT * FROM posts p WHERE MATCH(p.title) AGAINST(:keyword IN BOOLEAN MODE)", nativeQuery = true)
    List<PostJpaEntity> findByTitleFullText(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM posts p WHERE MATCH(p.title, p.content) AGAINST(:keyword IN BOOLEAN MODE)", nativeQuery = true)
    List<PostJpaEntity> findByTitleAndContentFullText(@Param("keyword") String keyword);
}