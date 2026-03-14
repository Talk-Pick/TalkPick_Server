package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import talkPick.core.common.model.TalkPickStatus;
import talkPick.domain.topic.domain.Topic;

import java.time.LocalDateTime;

public interface TopicJpaRepository extends JpaRepository<Topic, Long> {

    @Modifying
    @Query("UPDATE Topic t SET t.status = :disActive WHERE t.status = :active AND t.expiredAt IS NOT NULL AND t.expiredAt <= :now")
    int expireTopics(@Param("active") TalkPickStatus active, @Param("disActive") TalkPickStatus disActive, @Param("now") LocalDateTime now);
}
