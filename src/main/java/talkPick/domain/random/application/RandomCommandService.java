package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.domain.RandomTopicHistory;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.*;
import talkPick.domain.topic.port.out.TopicDataCacheManagerPort;
import talkPick.external.llm.port.LLMClientPort;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final MemberQueryRepositoryPort memberQueryRepositoryPort;
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;
    private final RandomTopicHistoryCommandRepositoryPort randomTopicCommandRepositoryPort;
    private final RandomTopicHistoryQueryRepositoryPort randomTopicHistoryQueryRepositoryPort;
    private final TopicDataCacheManagerPort topicDataCacheManagerPort;
    private final LLMClientPort llmClientPort;

    @Override
    public void start(Long memberId) {
        randomCommandRepositoryPort.save(Random.from(memberId));
    }

    /**
     * LLM_SERVER로 전달
     * Redis Cache -> 사용자 정보
     *  DB -> 사용자 정보 이전 데이터 List
     *  JVM Cache -> 모든 Topic 데이터
     **/
    @Override
    public List<RandomResDTO.RandomTopic> selectCategory(Long memberId, RandomReqDTO.SelectCategory requestDTO) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, requestDTO.randomId()).start();
        var randomTopicHistoryData = RandomTopicHistoryDataDTO.from(randomTopicCommandRepositoryPort.selectCategory(memberId, requestDTO));
        var memberData = memberQueryRepositoryPort.findMemberDataById(memberId);
        var topicData = topicDataCacheManagerPort.getAll();

        return llmClientPort.random(randomTopicHistoryData, memberData, topicData);
    }

    @Override
    public List<RandomResDTO.RandomTopic> selectTopic(Long memberId, RandomReqDTO.SelectTopic requestDTO) {
        RandomTopicHistory randomTopicHistory = randomTopicCommandRepositoryPort.selectTopic(memberId, requestDTO);

        return null;
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).quit();
    }

    @Override
    public RandomResDTO.Result end(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).end();
        return randomTopicHistoryQueryRepositoryPort.getResult(randomId);
    }

    @Override
    public void saveResult(Long memberId, Long randomId, RandomReqDTO.Result requestDTO) {
        //TODO 테이블을 새로 만들까 고민 중.
    }
}
