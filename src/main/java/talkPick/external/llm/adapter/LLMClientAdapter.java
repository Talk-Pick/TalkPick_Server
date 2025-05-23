package talkPick.external.llm.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.random.dto.TopicDataDTO;
import talkPick.external.llm.dto.LLMReqDTO;
import talkPick.external.llm.exception.LLMException;
import talkPick.external.llm.port.LLMClientPort;
import talkPick.global.error.ErrorCode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LLMClientAdapter implements LLMClientPort {
    private final WebClient llmWebClient;

    @Override
    public List<RandomResDTO.RandomTopic> random(RandomTopicHistoryDataDTO randomTopicHistoryData, MemberDataDTO memberData, List<TopicDataDTO> topicData) {
        LLMReqDTO request = new LLMReqDTO(randomTopicHistoryData, memberData, topicData);

        try {
            return llmWebClient.post()
                    .uri("/api/v1/llm/recommend")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToFlux(RandomResDTO.RandomTopic.class)
                    .retry(2)
                    .collectList()
                    .block();
        } catch (WebClientResponseException ex) {
            log.error("[LLMClientAdapter] LLM 서버 응답 실패: {}", ex.getResponseBodyAsString());
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM 서버 응답 실패: " + ex.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[LLMClientAdapter] LLM 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM 요청 중 오류 발생: " + e.getMessage());
        }
    }
}