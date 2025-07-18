package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDto;
import talkPick.domain.member.adapter.in.dto.MemberMbtiUpdateRequestDto;

import java.io.IOException;

public interface MemberCommandApi {

    ResponseEntity<?> joinEmailMember(@RequestBody MemberEmailReqDto memberReqDto, HttpServletResponse response);
    String showMbtiForm(HttpServletRequest request, HttpServletResponse response)throws IOException;
    ResponseEntity<?> updateMemberMbti(@RequestBody MemberMbtiUpdateRequestDto request);
}
