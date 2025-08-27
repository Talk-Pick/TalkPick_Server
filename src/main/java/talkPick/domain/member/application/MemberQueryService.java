package talkPick.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.in.MemberQueryUseCase;
import talkPick.domain.member.port.out.MemberLikedTopicsQueryRepositoryPort;
import talkPick.domain.member.port.out.MemberTopicResultQueryRepositoryPort;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.MemberExceptionHandler;
import talkPick.global.response.CursorPageResponse;
import talkPick.global.security.jwt.util.JwtProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberJpaRepository memberJpaRepository;
    private final MemberLikedTopicsQueryRepositoryPort memberLikedTopicsQueryRepositoryPort;
    private final MemberTopicResultQueryRepositoryPort memberTopicResultQueryRepositoryPort;
    private final JwtProvider jwtProvider;

    // 회원 프로필 조회 로직
    @Override
    public MemberResDto.ProfileResponse getProfile(String authorization) {
        // JWT 토큰에서 회원 ID 추출
        Long memberId = jwtProvider.getMemberId(authorization);

        // 회원 존재 여부 검증 및 조회
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 조회된 회원 정보를 DTO로 변환 후 반환
        return MemberConverter.toProfileResponse(findMember);
    }

    /**
     * 커서 기반 회원이 좋아요한 토픽 목록 조회
     */
    @Override
    public CursorPageResponse<MemberResDto.MemberLikedTopicResDto> getMemberLikedTopics(String authorization, LocalDateTime cursor, int size) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // size + 1개 조회하여 다음 페이지 존재 여부 판단
        List<MemberResDto.MemberLikedTopicResDto> memberLikedTopics = memberLikedTopicsQueryRepositoryPort.findMemberLikedTopics(findMember, cursor, size + 1);

        // 다음 페이지 존재 시 마지막 데이터 제거
        boolean hasNext = memberLikedTopics.size() > size;
        if (hasNext) memberLikedTopics.remove(memberLikedTopics.size() - 1);

        // 다음 페이지 조회용 커서 생성
        CursorPageResponse.Cursor nextCursor = null;
        if (hasNext && !memberLikedTopics.isEmpty()) {
            MemberResDto.MemberLikedTopicResDto last = memberLikedTopics.get(memberLikedTopics.size() - 1);
            nextCursor = new CursorPageResponse.Cursor(last.getCreatedDate(), last.getId());
        }

        // 커서 기반 페이징 응답 반환
        return CursorPageResponse.<MemberResDto.MemberLikedTopicResDto>builder()
                .items(memberLikedTopics)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    /**
     * 특정 일자 기준 회원 토픽 캘린더 결과 조회
     */
    @Override
    public CursorPageResponse<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(String authorization, LocalDate date) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        return memberTopicResultQueryRepositoryPort.findMemberTopicResults(findMember, date);
    }
}
