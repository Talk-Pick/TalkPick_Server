package talkPick.external.llm.adapter;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import talkPick.domain.random.adapter.out.dto.RandomResDTO;
import talkPick.domain.random.dto.MemberDataDTO;
import talkPick.domain.random.dto.RandomTopicHistoryDataDTO;
import talkPick.domain.topic.dto.TopicCacheDTO;
import talkPick.external.llm.adapter.dto.LLMReqDTO;
import talkPick.external.llm.constants.LLMConstants;
import talkPick.external.llm.exception.LLMException;
import talkPick.external.llm.port.LLMClientPort;
import talkPick.global.exception.ErrorCode;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class LLMClientAdapter implements LLMClientPort {
    private final WebClient llmWebClient;

    @Override
    @CircuitBreaker(name = LLMConstants.CIRCUIT_BREAKER_NAME, fallbackMethod = "")
    public List<RandomResDTO.RandomTopic> getRandomTopics(List<RandomTopicHistoryDataDTO> randomTopicHistoryData, MemberDataDTO memberData) {
        LLMReqDTO request = new LLMReqDTO(randomTopicHistoryData, memberData);

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
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM getRandomTopics 서버 응답 실패: " + ex.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[LLMClientAdapter] LLM 요청 중 오류 발생: {}", e.getMessage(), e);
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM getRandomTopics 요청 중 오류 발생: " + e.getMessage());
        }
    }

    @Override
    @CircuitBreaker(name = LLMConstants.CIRCUIT_BREAKER_NAME, fallbackMethod = "")
    public void send(List<TopicCacheDTO> topicCaches) {
        try {
            llmWebClient.post()
                    .uri("/api/v1/llm/send")
                    .bodyValue(topicCaches)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
            log.info("[LLMClientAdapter] Topic cache 전송 성공 - 전송 개수: {}", topicCaches.size());
        } catch (WebClientResponseException ex) {
            log.error("[LLMClientAdapter] topic-cache 응답 실패: {}", ex.getResponseBodyAsString());
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM 서버 send 응답 실패: " + ex.getResponseBodyAsString());
        } catch (Exception e) {
            log.error("[LLMClientAdapter] topic-cache 요청 중 오류: {}", e.getMessage(), e);
            throw new LLMException(ErrorCode.LLM_REQUEST_FAILED, "LLM 서버 send 요청 중 오류: " + e.getMessage());
        }
    }

    /**
     * TODO circuit breaker fallback 메서드 필요합니다
     * **/
}