package talkPick.domain.member.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberTermJpaRepository;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.MemberLoginHistory;
import talkPick.domain.member.domain.type.LoginType;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.adapter.out.dto.MemberResDto;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.domain.member.port.out.MemberLoginHistoryCommandRepositoryPort;
import talkPick.domain.term.adapter.out.repository.TermJpaRepository;
import talkPick.domain.term.domain.Term;
import talkPick.global.security.jwt.repository.RefreshTokenRepository;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.MemberExceptionHandler;
import talkPick.global.exception.handler.TermExceptionHandler;
import talkPick.global.model.TalkPickStatus;
import talkPick.global.security.jwt.util.JwtProvider;
import talkPick.domain.member.adapter.out.repository.MemberTopicResultJpaRepository;
import talkPick.domain.topic.domain.member.MemberTopicResult;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {
    private static final String DEFAULT_PROFILE_IMG_URL = "https://example.com/images/default-profile.png";

    private final MemberJpaRepository memberJpaRepository;
    private final TermJpaRepository termJpaRepository;
    private final MemberTermJpaRepository memberTermJpaRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberLoginHistoryCommandRepositoryPort memberLoginHistoryRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MemberTopicResultJpaRepository memberTopicResultJpaRepository;

    /**
     * 회원 프로필 수정
     */
    @Override
    public MemberResDto.ProfileUpdateResponse updateProfile(String authorization, MemberReqDto.ProfileUpdateRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        // 회원 조회
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 요청된 필드별 수정 처리
        if (request.getNickname() != null) findMember.updateNickname(request.getNickname());
        if (request.getGender() != null) findMember.updateGender(request.getGender());
        if (request.getBirth() != null) findMember.updateBirth(request.getBirth());
        if (request.getMbti() != null) findMember.updateMbti(request.getMbti());

        memberJpaRepository.save(findMember);

        return MemberConverter.toProfileUpdateResponse(findMember);
    }

    /**
     * 이메일 회원 신규 생성
     */
    @Override
    public Member findOrCreateEmailMember(MemberReqDto.MemberEmailReqest emailReqDto) {
        validatePassword(emailReqDto.getPassword());

        if (memberJpaRepository.findByEmail(emailReqDto.getEmail()).isPresent()) {
            throw new MemberExceptionHandler(ErrorCode.MEMBER_EMAIL_ALREADY_EXISTS);
        }

        Member newMember = MemberConverter.toEmailMember(emailReqDto);

        // 비밀번호 암호화
        newMember.updatePassword(passwordEncoder.encode(emailReqDto.getPassword()));
        return memberJpaRepository.save(newMember);
    }

    /**
     * 이메일 로그인 처리 (비밀번호 검증 및 로그인 이력 기록)
     */
    @Override
    public Member loginEmailMember(MemberReqDto.MemberEmailReqest emailReqDto) {
        validatePassword(emailReqDto.getPassword());

        Member member = memberJpaRepository.findByEmail(emailReqDto.getEmail())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 비밀번호 복호화 및 검증
        if (!passwordEncoder.matches(emailReqDto.getPassword(), member.getPassword())) {
            throw new MemberExceptionHandler(ErrorCode.INVALID_PASSWORD);
        }

        MemberLoginHistory loginHistory = MemberConverter.toLoginHistory(member);
        memberLoginHistoryRepository.save(loginHistory);

        return member;
    }

    /**
     * 카카오 멤버 데이터로 기존 회원 조회 또는 신규 생성
     */
    @Override
    public Member findOrCreateKakaoMember(MemberDataDto.KakaoMemberData kakaoMemberData) {
        Member findOrNewMember = memberJpaRepository.findByProviderId(kakaoMemberData.getSub())
                .orElseGet(() -> MemberConverter.toKakaoMember(kakaoMemberData));

        return memberJpaRepository.save(findOrNewMember);
    }

    /**
     * 회원 가입(추가 정보 입력 및 상태 변경 처리)
     */
    @Override
    public MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        if (!validateAdditionalInfo(request)) {
            throw new MemberExceptionHandler(ErrorCode.INVALID_MEMBER_INFO);
        }

        // 추가 정보 입력
        findMember.updateBirth(request.getBirth());
        findMember.updateGender(request.getGender());
        findMember.updateMbti(request.getMbti());
        findMember.updateNickname(request.getNickname());

        String profileImgUrl = request.getProfileImgUrl();
        if (profileImgUrl == null || profileImgUrl.trim().isEmpty()) {
            findMember.updateProfileImgUrl(DEFAULT_PROFILE_IMG_URL);
        } else {
            findMember.updateProfileImgUrl(profileImgUrl);
        }

        // 회원 ACTIVE 상태 변경
        findMember.updateStatus(TalkPickStatus.ACTIVE);
        memberJpaRepository.save(findMember);

        // 이메일 회원은 임시 토큰 삭제 처리
        if (findMember.getLoginType() == LoginType.EMAIL) {
            refreshTokenRepository.deleteByMemberId(findMember.getId());
        }

        // 카카오 회원은 가입 완료 시 로그인 기록 저장
        if (findMember.getLoginType() == LoginType.KAKAO) {
            MemberLoginHistory loginHistory = MemberConverter.toLoginHistory(findMember);
            memberLoginHistoryRepository.save(loginHistory);
        }

        return MemberConverter.toMemberSignupResponse(findMember);
    }

    /**
     * 회원 약관 동의 처리 (동의, 미동의 약관 저장 및 회원 상태 변경)
     */
    @Override
    public MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        List<Long> agreeTermIdList = request.getAgreeTermIdList();
        List<Long> disagreeTermIdList = request.getDisagreeTermIdList();

        if (!validateRequiredTerms(agreeTermIdList)) {
            throw new TermExceptionHandler(ErrorCode.REQUIRED_TERM_NOT_AGREED);
        }

        // 동의한 약관 저장 및 업데이트
        for (Long termId : agreeTermIdList) {
            Term term = termJpaRepository.findById(termId)
                    .orElseThrow(() -> new TermExceptionHandler(ErrorCode.TERM_NOT_FOUND));
            memberTermJpaRepository.findByMemberIdAndTerm(findMember.getId(), term)
                    .ifPresentOrElse(
                            mt -> mt.updateIsAgree(true),
                            () -> memberTermJpaRepository.save(MemberConverter.toMemberTerm(findMember, term, true))
                    );
        }

        // 미동의한 약관 저장 및 업데이트
        for (Long termId : disagreeTermIdList) {
            Term term = termJpaRepository.findById(termId)
                    .orElseThrow(() -> new TermExceptionHandler(ErrorCode.TERM_NOT_FOUND));
            memberTermJpaRepository.findByMemberIdAndTerm(findMember.getId(), term)
                    .ifPresentOrElse(
                            mt -> mt.updateIsAgree(false),
                            () -> memberTermJpaRepository.save(MemberConverter.toMemberTerm(findMember, term, false))
                    );
        }

        // 회원 상태 변경 및 저장
        findMember.updateStatus(TalkPickStatus.AGREE);
        memberJpaRepository.save(findMember);

        return MemberConverter.toTermAgreementResponse(findMember);
    }

    // 로그아웃 처리 (토큰 및 로그인 기록 삭제)
    @Override
    public void logout(String authorization) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        refreshTokenRepository.deleteByMemberId(findMember.getId());
        memberLoginHistoryRepository.deleteByMemberId(findMember.getId());
    }

    // 회원 탈퇴 처리 (상태 비활성화, 토큰 및 로그인 기록 삭제)
    @Override
    public void delete(String authorization) {
        Long memberId = jwtProvider.getMemberId(authorization);

        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        findMember.updateStatus(TalkPickStatus.DIS_ACTIVE);
        memberJpaRepository.save(findMember);

        refreshTokenRepository.deleteByMemberId(findMember.getId());
        memberLoginHistoryRepository.deleteByMemberId(findMember.getId());
    }

    // 토픽 캘린더 조회 코멘트 수정
    @Override
    public void TopicResultCommentChange(String authorization, MemberReqDto.TopicResultCommentChangeRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        // 회원 조회
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // MemberTopicResult 조회 (member_topic_history_id로 조회)
        MemberTopicResult findMemberTopicResult = memberTopicResultJpaRepository.findByMemberTopicHistoryId(request.getMemberTopicHistoryId())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 본인의 토픽 결과인지 검증
        if (!findMemberTopicResult.getMemberId().equals(findMember.getId())) {
            throw new MemberExceptionHandler(ErrorCode.MEMBER_NOT_FOUND);
        }

        // 코멘트 업데이트
        findMemberTopicResult.updateComment(request.getComment());
        memberTopicResultJpaRepository.save(findMemberTopicResult);
    }

    // 회원 가입 시 필수 정보 검증
    private boolean validateAdditionalInfo(MemberReqDto.MemberSignupRequest request) {
        return request.getNickname() != null &&
                request.getMbti() != null &&
                request.getGender() != null &&
                request.getBirth() != null;
    }

    // 필수 약관 동의 여부 검증
    private boolean validateRequiredTerms(List<Long> agreeTermIdList) {
        if (agreeTermIdList == null) return false;

        List<Term> requiredTerms = termJpaRepository.findByIsRequiredTrue();

        for (Term term : requiredTerms) {
            if (!agreeTermIdList.contains(term.getId())) {
                return false;
            }
        }
        return true;
    }

    // 비밀번호 유효성 검증 (영문 대소문자, 숫자, 특수문자 포함 8~20자)
    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new MemberExceptionHandler(ErrorCode.PASSWORD_REQUIRED);
        }

        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$";
        if (!password.matches(passwordPattern)) {
            throw new MemberExceptionHandler(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }
}
