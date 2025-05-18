package talkPick.domain.member.application;

import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.member.port.in.MemberCommandUseCase;
import talkPick.global.error.exception.member.MemberServiceException;

import java.util.Optional;

import static talkPick.domain.member.application.mapper.MemberMapper.fromDtoToMember;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    @Override
    public void setEmailMember(MemberEmailReqDTO memberReqDto) {
        Member saveMember = fromDtoToMember(memberReqDto);

        memberJpaRepository.save(saveMember);
    }

    @Transactional
    @Override
    public Member setKakaoMember(Member member) {
        try {
            return memberJpaRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            // 데이터 무결성 위반 예외 (예: 중복 키, 제약 조건 위반 등)
            throw new MemberServiceException("회원 또는 프로필 저장 중 데이터 무결성 오류가 발생했습니다.", e);
        } catch (JpaSystemException | PersistenceException e) {
            // JPA 관련 시스템 예외
            throw new MemberServiceException("JPA 처리 중 오류가 발생했습니다.", e);
        } catch (Exception e) {
            // 기타 예외
            throw new MemberServiceException("회원 및 프로필 저장 중 오류가 발생했습니다.", e);
        }

    }

    @Transactional
    @Override
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


}
