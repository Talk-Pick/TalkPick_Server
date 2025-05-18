package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.auth.Role;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDTO;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.application.MemberCommandService;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/topic")
@Slf4j
public class MemberCommandController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화 인터페이스
    private boolean secureCookie = false; //개발 환경에서 false, 프로덕션에서 true


    //이메일 기반 회원가입
    @PostMapping("/members/join")
    public ResponseEntity<?> joinEmailMember(@RequestBody MemberEmailReqDTO memberReqDto, HttpServletResponse response) {
        log.info("이메일 회원가입 요청: {}", memberReqDto.getEmail());

        Optional<Member> existingMember = memberCommandService.findByEmail(memberReqDto.getEmail());
        if (existingMember.isPresent()) {
            return ResponseEntity.badRequest().body("이미 가입된 이메일입니다.");
        }


        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
        memberReqDto.setPassword(encodedPassword);

        try {
            // 회원 저장
            memberQueryService.setEmailMember(memberReqDto);
            Optional<Member> memberOpt = memberCommandService.findByEmail(memberReqDto.getEmail());

            if (memberOpt.isPresent()) {
                Member member = memberOpt.get();

                // JWT 토큰
                JwtResDTO.Login jwtToken = jwtProvider.createJwt(member.getId(), String.valueOf(Role.MEMBER));
                jwtProvider.addTokenCookies(response, jwtToken);
                return ResponseEntity.ok(new MemberEmailResDTO(member));
            } else {
                return ResponseEntity.badRequest().body("회원 가입에 실패했습니다.");
            }
        } catch (Exception e) {
            log.error("회원가입 처리 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원가입 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


}
