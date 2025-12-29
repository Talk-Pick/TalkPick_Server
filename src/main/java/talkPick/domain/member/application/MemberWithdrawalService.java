package talkPick.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.inquiry.adapter.out.repository.InquiryJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.adapter.out.repository.MemberLoginHistoryJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTermJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTopicHistoryJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTopicResultJpaRepository;
import talkPick.domain.member.port.in.MemberWithdrawalUseCase;
import talkPick.domain.random.adapter.out.repository.RandomJpaRepository;
import talkPick.domain.random.adapter.out.repository.RandomTopicHistoryJpaRepository;
import talkPick.domain.today.adapter.out.repository.TodayTopicJpaRepository;
import talkPick.domain.topic.adapter.out.repository.TopicLikeHistoryJpaRepository;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;
import talkPick.global.security.jwt.util.JwtProvider;

@Service
@RequiredArgsConstructor
public class MemberWithdrawalService implements MemberWithdrawalUseCase {

    private final JwtProvider jwtProvider;
    private final MemberJpaRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    
    // 연관 데이터 리포지토리들
    private final InquiryJpaRepository inquiryRepository;
    private final MemberTermJpaRepository memberTermRepository;
    private final MemberLoginHistoryJpaRepository memberLoginHistoryRepository;
    private final MemberTopicHistoryJpaRepository memberTopicHistoryRepository;
    private final MemberTopicResultJpaRepository memberTopicResultRepository;
    private final RandomJpaRepository randomRepository;
    private final RandomTopicHistoryJpaRepository randomTopicHistoryRepository;
    private final TodayTopicJpaRepository todayTopicRepository;
    private final TopicLikeHistoryJpaRepository topicLikeHistoryRepository;

    @Override
    @Transactional
    public void withdraw(String authorization) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 1. Refresh Token 삭제 (즉시 로그아웃 효과)
        refreshTokenRepository.deleteAllByMemberIdInBulk(memberId);

        // 2. 소프트 삭제 처리
        member.withdraw();
        memberRepository.save(member);
    }

    @Override
    @Transactional
    public void hardDelete(Long memberId) {
        // 1. 연관 데이터 일괄 삭제
        inquiryRepository.deleteAllByMemberIdInBulk(memberId);
        memberTermRepository.deleteAllByMemberIdInBulk(memberId);
        memberLoginHistoryRepository.deleteAllByMemberIdInBulk(memberId);
        memberTopicHistoryRepository.deleteAllByMemberIdInBulk(memberId);
        memberTopicResultRepository.deleteAllByMemberIdInBulk(memberId);
        randomRepository.deleteAllByMemberIdInBulk(memberId);
        randomTopicHistoryRepository.deleteAllByMemberIdInBulk(memberId);
        todayTopicRepository.deleteAllByMemberIdInBulk(memberId);
        topicLikeHistoryRepository.deleteAllByMemberIdInBulk(memberId);

        // 2. 회원 영구 삭제
        memberRepository.deleteById(memberId);
    }
}
