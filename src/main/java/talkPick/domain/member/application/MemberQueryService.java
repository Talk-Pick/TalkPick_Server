package talkPick.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.dto.*;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.port.in.MemberQueryUseCase;
import talkPick.domain.member.port.out.MemberLikedTopicsQueryRepositoryPort;
import talkPick.domain.member.port.out.MemberTopicResultQueryRepositoryPort;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.MemberHandler;
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

    @Override
    public MemberResDto.ProfileResponse getProfile(String authorization) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorCode.MEMBER_NOT_FOUND));

        return MemberConverter.toProfileResponse(findMember);
    }
    // 수정 사항
    @Override
    public CursorPageResponse<MemberResDto.MemberLikedTopicResDto> getMemberLikedTopics(String authorization, LocalDateTime cursor, int size) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorCode.MEMBER_NOT_FOUND));

        List<MemberResDto.MemberLikedTopicResDto> memberLikedTopics = memberLikedTopicsQueryRepositoryPort.findMemberLikedTopics(findMember, cursor, size);

        boolean hasNext = memberLikedTopics.size() > size;
        if (hasNext) memberLikedTopics.remove(memberLikedTopics.size() - 1);

        CursorPageResponse.Cursor nextCursor = null;
        if (hasNext && !memberLikedTopics.isEmpty()) {
            MemberResDto.MemberLikedTopicResDto last = memberLikedTopics.get(memberLikedTopics.size() - 1);

            nextCursor = new CursorPageResponse.Cursor(last.getCreatedDate(), last.getId());
        }

        return CursorPageResponse.<MemberResDto.MemberLikedTopicResDto>builder()
                .items(memberLikedTopics)
                .hasNext(hasNext)
                .nextCursor(nextCursor)
                .build();
    }

    @Override
    public Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(String authorization, LocalDate date, Pageable pageable) {
        return memberTopicResultQueryRepositoryPort.findMemberTopicResults(authorization, date, pageable);
    }


}
