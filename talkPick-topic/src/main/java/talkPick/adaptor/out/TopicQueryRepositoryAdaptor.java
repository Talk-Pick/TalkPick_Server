package talkPick.adaptor.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.adaptor.out.repository.TopicJpaRepository;
import talkPick.adaptor.out.repository.TopicQuerydslRepository;
import talkPick.constants.topic.TopicConstants;
import talkPick.domain.type.Category;
import talkPick.infrastructure.cache.model.TopicRanking;
import talkPick.model.PageCustom;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdaptor implements TopicQueryRepositoryPort {
    private final RedisTemplate<String, TopicRanking> redisTemplate;
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public List<TopicResDTO.Topics> findTopLikedTopics() {
        //TODO Redis에서 조회해야 함.
        return topicQuerydslRepository.findTopLikedTopics(TopicConstants.TOP_LIKED_TOPICS_COUNT);
    }

    @Override
    public List<TopicResDTO.Categories> findTopCategories() {
        //TODO Redis+DB 할 때 중복 제거해야 함.

        List<Category> topCategoriesFromRedis = findTopCategoriesFromRedis(TopicConstants.TOP_CATEGORIES_COUNT);

        if (topCategoriesFromRedis.size() < TopicConstants.TOP_CATEGORIES_COUNT) {
            List<Category> topCategoriesFromDB = findTopCategoriesFromDB(TopicConstants.TOP_CATEGORIES_COUNT - topCategoriesFromRedis.size());
            topCategoriesFromRedis.addAll(topCategoriesFromDB);
        }

        return topCategoriesFromRedis.stream()
                .map(category -> new TopicResDTO.Categories(category.name(), category.getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public PageCustom<TopicResDTO.Categories> findCategoriesWithPageable(Pageable pageable) {
        return topicQuerydslRepository.findCategoriesWithPageable(pageable);
    }

    private List<Category> findTopCategoriesFromRedis(int count) {
        Set<String> keys = redisTemplate.keys(TopicConstants.REDIS_KEY_PREFIX + "*");
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyList();
        }

        List<TopicRanking> rankings = redisTemplate.opsForValue().multiGet(keys);
        if (rankings == null || rankings.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Category, Long> categoryCountMap = rankings.stream()
                .collect(Collectors.groupingBy(TopicRanking::getCategory, Collectors.counting()));

        return categoryCountMap.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private List<Category> findTopCategoriesFromDB(int count) {
        return topicQuerydslRepository.findTopCategories(count);
    }
}