package talkPick.domain.member.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MemberQueryUseCase {
    List<MemberEmailResDTO> getEmailMembers();
    List<MemberKakaoResDTO> getkakaoMembers();
    Page<MemberLikedTopicsResDto> getMemberLikedTopics(Long memberId, Pageable pageable);
    Page<MemberTopicResultResDto> getMemberTopicResultsByCreatedDate(Long memberId, LocalDate date, Pageable pageable);
    MemberDetailResDto getMemberInfo(Long memberId);
    Optional<Member> findByKakaoId(String kakaoId);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);

}
