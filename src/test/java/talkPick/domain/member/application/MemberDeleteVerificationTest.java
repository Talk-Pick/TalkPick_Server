package talkPick.domain.member.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import talkPick.domain.inquiry.adapter.out.repository.InquiryJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTopicResultJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.out.MemberCommandRepositoryPort;
import talkPick.domain.member.port.out.MemberLoginHistoryCommandRepositoryPort;
import talkPick.domain.member.port.out.MemberQueryRepositoryPort;
import talkPick.domain.member.port.out.MemberTermCommandRepositoryPort;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.term.port.out.TermQueryRepositoryPort;
import talkPick.domain.today.adapter.out.repository.TodayTopicJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTopicHistoryJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;
import talkPick.global.security.jwt.util.JwtProvider;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberDeleteVerificationTest {

    @InjectMocks
    private MemberCommandService memberCommandService;

    @Mock private MemberCommandRepositoryPort memberCommandRepositoryPort;
    @Mock private TermQueryRepositoryPort termQueryRepositoryPort;
    @Mock private MemberTermCommandRepositoryPort memberTermJpaRepository;
    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private MemberLoginHistoryCommandRepositoryPort memberLoginHistoryRepository;
    @Mock private MemberQueryRepositoryPort memberQueryRepositoryPort;
    @Mock private JwtProvider jwtProvider;
    @Mock private MemberTopicResultJpaRepository memberTopicResultJpaRepository;
    @Mock private MemberJpaRepository memberJpaRepository;
    @Mock private InquiryJpaRepository inquiryJpaRepository;
    @Mock private RandomJpaRepository randomJpaRepository;
    @Mock private RandomTopicHistoryJpaRepository randomTopicHistoryJpaRepository;
    @Mock private TodayTopicJpaRepository todayTopicJpaRepository;
    @Mock private TopicLikeHistoryJpaRepository topicLikeHistoryJpaRepository;
    @Mock private MemberTopicHistoryJpaRepository memberTopicHistoryJpaRepository;

    @Test
    @DisplayName("회원 탈퇴 시 모든 연관 데이터가 삭제되어야 한다")
    void delete_shouldDeleteAllRelatedData() {
        // given
        String token = "Bearer token";
        Long memberId = 1L;
        Member mockMember = mock(Member.class);
        given(mockMember.getId()).willReturn(memberId);

        given(jwtProvider.getMemberId(token)).willReturn(memberId);
        given(memberQueryRepositoryPort.findMemberById(memberId)).willReturn(mockMember);
        given(refreshTokenRepository.findByMember(mockMember)).willReturn(Optional.empty());

        // when
        memberCommandService.delete(token);

        // then
        // 기존 삭제 로직 검증
        verify(memberLoginHistoryRepository).deleteByMemberId(memberId);
        verify(memberJpaRepository).deleteById(memberId);

        // 누락되었던 삭제 로직 검증
        verify(inquiryJpaRepository).deleteByMemberId(memberId);
        verify(memberTermJpaRepository).deleteByMemberId(memberId);
        verify(memberTopicResultJpaRepository).deleteByMemberId(memberId);
        verify(randomJpaRepository).deleteByMemberId(memberId);
        verify(randomTopicHistoryJpaRepository).deleteByMemberId(memberId);
        verify(todayTopicJpaRepository).deleteByMemberId(memberId);
        verify(topicLikeHistoryJpaRepository).deleteByMemberId(memberId);
        verify(memberTopicHistoryJpaRepository).deleteByMemberId(memberId);
    }
}
