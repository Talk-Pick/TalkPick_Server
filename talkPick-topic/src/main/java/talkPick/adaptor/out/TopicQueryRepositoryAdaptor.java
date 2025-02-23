package talkPick.adaptor.out;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import talkPick.adaptor.out.dto.TopicResDTO;
import talkPick.adaptor.out.repository.TopicJpaRepository;
import talkPick.adaptor.out.repository.TopicQuerydslRepository;
import talkPick.constants.topic.TopicConstants;
import talkPick.infrastructure.cache.model.TopicRanking;
import talkPick.port.out.TopicQueryRepositoryPort;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TopicQueryRepositoryAdaptor implements TopicQueryRepositoryPort {
    private final RedisTemplate<String, TopicRanking> redisTemplate;
    private final TopicQuerydslRepository topicQuerydslRepository;
    private final TopicJpaRepository topicJpaRepository;

    @Override
    public List<TopicResDTO.TopCategories> findTopCategories() {
//        Set<String> keys = redisTemplate.keys(TopicConstants.REDIS_KEY_PREFIX + "*");
//        if (keys == null || keys.isEmpty()) {
//            return getRandomCategoriesFromDB();
//        }
//        List<TopicRanking> rankings = redisTemplate.opsForValue().multiGet(keys);
//        if (rankings == null || rankings.isEmpty()) {
//            return getRandomCategoriesFromDB();
//        }

        return null;
    }
}