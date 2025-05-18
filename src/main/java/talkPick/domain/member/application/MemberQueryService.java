package talkPick.domain.member.application;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.domain.member.domain.Member;
import talkPick.global.error.exception.member.MemberServiceException;
import talkPick.domain.member.port.in.MemberQueryUseCase;

import java.util.List;
import java.util.stream.Collectors;

import static talkPick.domain.member.application.mapper.MemberMapper.fromDtoToMember;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {
    private final MemberJpaRepository memberJpaRepository;

    @Transactional
    public void setEmailMember(MemberEmailReqDTO memberReqDto) {
        Member saveMember = fromDtoToMember(memberReqDto);

        memberJpaRepository.save(saveMember);
    }


    public List<MemberEmailResDTO> getEmailMembers() {
        List<Member> all = memberJpaRepository.findAll();
        List<MemberEmailResDTO> memberResDtoList = all.stream()
                .filter(m -> m.getKakaoId() == null)
                .map(m -> new MemberEmailResDTO(m))
                .collect(Collectors.toList());
        return memberResDtoList;
    }

    @Transactional
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


    public List<MemberKakaoResDTO> getkakaoMembers() {
        List<Member> all = memberJpaRepository.findAll();
        List<MemberKakaoResDTO> memberResDtoList = all.stream()
                .filter(m -> m.getKakaoId() != null)
                .map(m -> new MemberKakaoResDTO(m))
                .collect(Collectors.toList());
        return memberResDtoList;
    }

    public MemberDetailResDto getMemberInfo(Long memberId) {
        Member member = memberJpaRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with id: " + memberId));
        return MemberDetailResDto.fromEntity(member);
    }
}
