package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.TopicImage;

public interface TopicImageJpaRepository extends JpaRepository<TopicImage, Long> {
}
