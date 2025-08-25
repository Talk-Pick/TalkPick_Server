package talkPick.domain.random.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.batch.topic.port.TopicCacheManager;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.domain.Random;
import talkPick.domain.random.port.in.RandomCommandUseCase;
import talkPick.domain.random.port.out.*;
import talkPick.external.llm.port.LLMClientPort;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RandomCommandService implements RandomCommandUseCase {
    private final MemberQueryRepositoryPort memberQueryRepositoryPort;
    private final RandomQueryRepositoryPort randomQueryRepositoryPort;
    private final RandomCommandRepositoryPort randomCommandRepositoryPort;
    private final RandomTopicHistoryCommandRepositoryPort randomTopicCommandRepositoryPort;
    private final RandomTopicHistoryQueryRepositoryPort randomTopicHistoryQueryRepositoryPort;
    private final TopicCacheManager topicCacheManager;
    private final LLMClientPort llmClientPort;

    @Override
    public void start(Long memberId) {
        randomCommandRepositoryPort.save(Random.from(memberId));
    }

    @Override
    public void next(Long memberId, Long randomId, RandomReqDTO.Next requestDTO) {
        randomTopicHistoryQueryRepositoryPort.getRandomTopicHistoryByMemberIdAndRandomIdAndOrder(memberId, randomId, requestDTO).next();
    }

    @Override
    public void quit(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).quit();
    }

    @Override
    public void end(Long memberId, Long randomId) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).end();
    }

    @Override
    public void record(Long memberId, Long randomId, RandomReqDTO.Record requestDTO) {
        randomTopicCommandRepositoryPort.record(memberId, randomId, requestDTO);
    }

    @Override
    public void rate(Long memberId, Long randomId, RandomReqDTO.Rate requestDTO) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).rate(requestDTO);
    }

    @Override
    public void comment(Long memberId, Long randomId, RandomReqDTO.Comment requestDTO) {
        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, randomId).comment(requestDTO);
    }

//    /**
//     * LLM_SERVER로 전달
//     * Redis Cache -> 사용자 정보
//     *  DB -> 사용자 정보 이전 데이터 List
//     *  JVM Cache -> 모든 Topic 데이터
//     **/
//    @Override
//    public List<RandomResDTO.RandomTopic> selectByCategories(Long memberId, RandomReqDTO.SelectByCategory requestDTO) {
//        randomQueryRepositoryPort.findRandomByMemberIdAndId(memberId, requestDTO.randomId()).start();
//        randomTopicCommandRepositoryPort.saveByCategory(memberId, requestDTO);
//
//        // TODO 추후 LLM 서버 적용 시, 사용할 예정
////        return sendToLLM(requestDTO.randomId(), memberId);
//        return topicCacheManager.getRandomTopics(0);
//    }

//    @Override
//    public List<RandomResDTO.RandomTopic> selectByTopics(Long memberId, RandomReqDTO.SelectByTopic requestDTO) {
//        randomTopicCommandRepositoryPort.save(memberId, requestDTO);
//        return topicCacheManager.getRandomTopics(requestDTO.order());
//    }

    private List<RandomResDTO.RandomTopic> sendToLLM(Long requestDTO, Long memberId) {
        var randomTopicHistoryData = randomTopicHistoryQueryRepositoryPort.getRandomTopicHistoriesByRandomId(requestDTO);
        var memberData = memberQueryRepositoryPort.findMemberDataById(memberId);
        return llmClientPort.getRandomTopics(randomTopicHistoryData, memberData);
    }
}
