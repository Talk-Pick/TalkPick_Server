package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.port.in.RandomQueryUseCase;
import talkPick.domain.random.port.out.RandomQueryRepositoryPort;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RandomQueryService implements RandomQueryUseCase {
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;

    @Override
    public List<RandomResDTO.Categories> getCategories() {
        return randomQueryRepositoryPort.findCategories();
    }

    /**
     * 1. 사용자 MBTI + 연령대 기준으로 일정 수량의 토픽 수집.
     * 2. 선택을 기반으로 위 수집된 토픽에서 필터링 후 상위 4개 제공.
     * 3. 제공 후 중복 방지를 위해 예외로 뺴놔야 함.
     * **/
    @Override
    public List<RandomResDTO.RandomTopic> getRandomTopics(Long memberId, Long randomId) {
        //TODO 랜덤 토픽 알고리즘 필요.
        //TODO 시나리오
        return List.of();
    }

    @Override
    public RandomResDTO.RandomTopicDetail getRandomTopicDetail(Long topicId) {
        RandomResDTO.RandomTopicDetail result = randomQueryRepositoryPort.findRandomTopicDetail(topicId);
        result.addTopicImage(randomQueryRepositoryPort.findTopicImages(topicId));
        return result;
    }
}
