package talkPick.domain.member.adapter.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberLikedTopicsResDto;
import talkPick.domain.member.adapter.in.dto.MemberTopicResultResDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;

import java.time.LocalDate;
import java.util.List;

public interface MemberQueryApi {
    List<MemberEmailResDTO> getEmailMembers();
    List<MemberKakaoResDTO> getKakaoMembers();
    MemberDetailResDto getMemberInfo();
    Page<MemberLikedTopicsResDto> getMemberLikedTopics(Pageable pageable);
    Page<MemberTopicResultResDto> getMemberTopicResults(@RequestParam("date") LocalDate date, Pageable pageable);
}
