package talkPick.domain.member.application;

import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.out.repository.MemberTermJpaRepository;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.domain.mapping.MemberTerm;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.member.dto.MemberReqDto;
import talkPick.domain.member.dto.MemberResDto;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.domain.term.adapter.out.repository.TermJpaRepository;
import talkPick.domain.term.domain.Term;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.MemberHandler;
import talkPick.global.exception.handler.TermHandler;
import talkPick.global.model.TalkPickStatus;
import talkPick.global.security.jwt.util.JwtProvider;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {
    private final MemberJpaRepository memberJpaRepository;
    private final TermJpaRepository termJpaRepository;
    private final MemberTermJpaRepository memberTermJpaRepository;
    private final JwtProvider jwtProvider;

    private static final String DEFAULT_PROFILE_IMG_URL = "https://example.com/images/default-profile.png";
    private final FileDescriptorMetrics fileDescriptorMetrics;


    @Override
    @Transactional
    public Member updateMemberMbti(Long memberId, MBTI mbti) {
        Optional<Member> memberOpt = memberJpaRepository.findById(memberId);
        if (memberOpt.isEmpty()) {
            throw new RuntimeException("회원을 찾을 수 없습니다. ID: " + memberId);
        }

        Member member = memberOpt.get();

        // MBTI 값 업데이트
        member.setMbti(mbti);

        // 저장 및 반환
        return memberJpaRepository.save(member);

    }

    @Override
    public Member findOrCreateEmailMember(MemberReqDto.MemberEmailReqDto emailReqDto) {

        // 비밀번호 검증
        validatePassword(emailReqDto.getPassword());

        Member findOrNewMember = memberJpaRepository.findByEmail(emailReqDto.getEmail())
                .orElseGet(() -> MemberConverter.toEmailMember(emailReqDto));

        return memberJpaRepository.save(findOrNewMember);
    }

    /**
     * id_token 에서 추출한 멤버 데이터를 통해
     * 기존 회원이라면 조회해서 가져오고
     * 첫 로그인이면 데이터 저장하는 메서드
     * @param kakaoMemberData : id_token 에서 추출한 멤버 데이터
     * @return : 추출한 멤버 데이터와 일치하는 멤버
     */
    @Override
    @Transactional
    public Member findOrCreateKakaoMember(MemberDataDto.KakaoMemberData kakaoMemberData) {
        Member findOrNewMember = memberJpaRepository.findByProviderId(kakaoMemberData.getSub())
                .orElseGet(() -> MemberConverter.toKakaoMember(kakaoMemberData));

        return memberJpaRepository.save(findOrNewMember);
    }

    @Override
    public MemberResDto.MemberSignupResponse memberSignup(String authorization, MemberReqDto.MemberSignupRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorCode.MEMBER_NOT_FOUND));

        if (!validateAdditionalInfo(request)) {
            throw new MemberHandler(ErrorCode.INVALID_MEMBER_INFO);
        }

        // 추가 정보 저장
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

        findMember.updateStatus(TalkPickStatus.ACTIVE);
        memberJpaRepository.save(findMember);

        return MemberConverter.toMemberSignupResponse(findMember);
    }

    @Override
    public MemberResDto.TermAgreementResponse termAgreement(String authorization, MemberReqDto.TermAgreementRequest request) {
        Long memberId = jwtProvider.getMemberId(authorization);

        // 회원 조회
        Member findMember = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new MemberHandler(ErrorCode.MEMBER_NOT_FOUND));

        // 약관 동의 정보 저장
        List<Long> agreeTermIdList = request.getAgreeTermIdList();
        List<Long> disagreeTermIdList = request.getDisagreeTermIdList();

        if (!validateRequiredTerms(agreeTermIdList)) {
            throw new TermHandler(ErrorCode.REQUIRED_TERM_NOT_AGREED);
        }

        // 동의한 약관
        for (Long termId : agreeTermIdList) {
            Term term = termJpaRepository.findById(termId)
                    .orElseThrow(() -> new TermHandler(ErrorCode.TERM_NOT_FOUND));

            memberTermJpaRepository.findByMemberAndTerm(findMember, term)
                    .ifPresentOrElse(
                            mt -> mt.updateIsAgree(true),
                            () -> {
                                MemberTerm newMemberTerm = MemberConverter.toMemberTerm(findMember, term, true);
                                memberTermJpaRepository.save(newMemberTerm);
                            }
                    );
        }

        // 미동의한 약관
        for (Long termId : disagreeTermIdList) {
            Term term = termJpaRepository.findById(termId)
                    .orElseThrow(() -> new TermHandler(ErrorCode.TERM_NOT_FOUND));
            memberTermJpaRepository.findByMemberAndTerm(findMember, term)
                    .ifPresentOrElse(
                            mt -> mt.updateIsAgree(false),
                            () -> {
                                MemberTerm newMemberTerm = MemberConverter.toMemberTerm(findMember, term, false);
                                memberTermJpaRepository.save(newMemberTerm);
                            }
                    );
        }

        findMember.updateStatus(TalkPickStatus.AGREE);
        memberJpaRepository.save(findMember);

        return MemberConverter.toTermAgreementResponse(findMember);


    }

    // 추가 정보 필수 입력값 검증
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

    private void validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new MemberHandler(ErrorCode.PASSWORD_REQUIRED);
        }
        // 상용 서비스에서 흔히 쓰이는 비밀번호 정규식 (영문 대소문자, 숫자, 특수문자 포함 8~20자)
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,20}$";

        if (!password.matches(passwordPattern)) {
            throw new MemberHandler(ErrorCode.INVALID_PASSWORD_FORMAT);
        }
    }



}
