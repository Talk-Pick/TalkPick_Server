package talkPick.performance;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import talkPick.domain.inquiry.adapter.out.repository.InquiryJpaRepository;
import talkPick.domain.inquiry.domain.Inquiry;
import talkPick.domain.inquiry.domain.type.InquiryType;
import talkPick.domain.member.adapter.out.repository.MemberTopicHistoryJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTopicResultJpaRepository;
import talkPick.domain.topic.domain.member.MemberTopicHistory;
import talkPick.domain.topic.domain.member.MemberTopicResult;
import talkPick.domain.topic.domain.type.TopicType;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MemberDeletePerformanceTest {

    @Autowired InquiryJpaRepository inquiryRepository;
    @Autowired MemberTopicHistoryJpaRepository historyRepository;
    @Autowired MemberTopicResultJpaRepository resultRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원 삭제 시 쿼리 발생 횟수 비교 (기존 vs 벌크)")
    void compareQueryCounts() {
        Long memberId = 99999L; // 테스트용 가상 ID

        // Case 1: 기존 방식 (JPA Delete)
        setupData(memberId);
        em.flush();
        em.clear();

        inquiryRepository.deleteByMemberId(memberId);
        historyRepository.deleteByMemberId(memberId);
        resultRepository.deleteByMemberId(memberId);
        em.flush();

        // Case 2: 벌크 방식 (@Modifying)
        setupData(memberId);
        em.flush();
        em.clear();

        inquiryRepository.deleteAllByMemberIdInBulk(memberId);
        historyRepository.deleteAllByMemberIdInBulk(memberId);
        resultRepository.deleteAllByMemberIdInBulk(memberId);
    }

    private void setupData(Long memberId) {
        int count = 100; // 각 엔티티 당 100개씩 생성

        // 1. Inquiry 생성
        List<Inquiry> inquiries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            inquiries.add(Inquiry.builder()
                    .memberId(memberId)
                    .title("테스트 문의 " + i)
                    .content("내용입니다.")
                    .email("test" + i + "@example.com")
                    .type(InquiryType.GENERAL)
                    .isAnswered(false)
                    .build());
        }
        inquiryRepository.saveAll(inquiries);

        // 2. MemberTopicHistory 생성
        List<MemberTopicHistory> histories = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            histories.add(MemberTopicHistory.builder()
                    .memberId(memberId)
                    .topicId(100L + i)
                    .talkTime(60000L)
                    .checkLiked(false)
                    .sequence(i)
                    .topicType(TopicType.SELECTED)
                    .build());
        }
        historyRepository.saveAll(histories);

        // 3. MemberTopicResult 생성
        List<MemberTopicResult> results = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            results.add(MemberTopicResult.builder()
                    .memberId(memberId)
                    .memberTopicHistoryId(200L + i)
                    .comment("결과 코멘트 " + i)
                    .build());
        }
        resultRepository.saveAll(results);
    }
}
