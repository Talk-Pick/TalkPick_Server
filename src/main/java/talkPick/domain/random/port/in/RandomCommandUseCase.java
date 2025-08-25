package talkPick.domain.random.port.in;

import talkPick.domain.random.adapter.in.dto.RandomReqDTO;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import java.util.List;

public interface RandomCommandUseCase {
    void start(Long memberId);
    void quit(Long memberId, Long randomId);
    void end(Long memberId, Long randomId);
    void rate(Long memberId, Long randomId, RandomReqDTO.Rate requestDTO);
    void comment(Long memberId, Long randomId, RandomReqDTO.Comment requestDTO);

    List<RandomResDTO.RandomTopic> selectByCategories(Long memberId, RandomReqDTO.SelectByCategory requestDTO);
    List<RandomResDTO.RandomTopic> selectByTopics(Long memberId, RandomReqDTO.SelectByTopic requestDTO);
}
