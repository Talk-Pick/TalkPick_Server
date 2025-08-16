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
import talkPick.global.security.jwt.util.JwtProvider;


import java.time.LocalDate;
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

    @Override
    public Page<MemberResDto.MemberLikedTopicsResDto> getMemberLikedTopics(Long memberId, Pageable pageable) {
        return memberLikedTopicsQueryRepositoryPort.findMemberLikedTopics(memberId, pageable);
    }

    @Override
    public Page<MemberResDto.MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(Long memberId, LocalDate date, Pageable pageable) {
        return memberTopicResultQueryRepositoryPort.findMemberTopicResults(memberId, date, pageable);
    }


}
