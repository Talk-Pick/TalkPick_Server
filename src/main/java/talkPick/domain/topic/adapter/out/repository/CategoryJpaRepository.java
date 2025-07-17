package talkPick.domain.topic.adapter.out.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkPick.domain.topic.domain.Category;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {
}
