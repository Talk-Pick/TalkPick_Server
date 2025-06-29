package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.auth.Role;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.in.dto.MemberDetailResDto;
import talkPick.domain.member.adapter.in.dto.MemberEmailReqDto;
import talkPick.domain.member.adapter.in.dto.MemberMbtiUpdateRequestDto;
import talkPick.domain.member.adapter.out.dto.MemberEmailResDTO;
import talkPick.domain.member.application.MemberCommandService;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MemberCommandController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder; //비밀번호 암호화 인터페이스


    //이메일 기반 회원가입
    @PostMapping("/members/join")
    public ResponseEntity<?> joinEmailMember(@RequestBody MemberEmailReqDto memberReqDto, HttpServletResponse response) {
        log.info("이메일 회원가입 요청: {}", memberReqDto.getEmail());

        Optional<Member> existingMember = memberQueryService.findByEmail(memberReqDto.getEmail());
        if (existingMember.isPresent()) {
            return ResponseEntity.badRequest().body("이미 가입된 이메일입니다.");
        }


        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(memberReqDto.getPassword());
        memberReqDto.setPassword(encodedPassword);

        try {
            // 회원 저장
            memberCommandService.setEmailMember(memberReqDto);
            Optional<Member> memberOpt = memberQueryService.findByEmail(memberReqDto.getEmail());

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

    //mbti 입력 페이지
    @GetMapping("/topic/additional")
    public String showMbtiForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("mbti 입력 페이지");
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userInfo") == null) {
            log.error("세션이 만료되었거나 카카오 사용자 정보가 없습니다.");
            return "redirect:/oauth/kakao/authorize"; // 스프링 MVC의 리다이렉트 방식
        }

        KakaoUserInfo userInfo = (KakaoUserInfo) session.getAttribute("userInfo");

        // 뷰 이름 반환
        return "mbti-form";
    }

    // mbti 수정
    @PutMapping("/members/mbti")
    public ResponseEntity<?> updateMemberMbti(@RequestBody MemberMbtiUpdateRequestDto request) {
        log.info("회원 MBTI 수정 요청 -  MBTI: {}", request.getMbti());
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            long memberId = Long.parseLong(authentication.getName());

            Optional<Member> memberOpt = memberQueryService.findById(memberId);
            if (memberOpt.isEmpty()) {
                log.error("회원을 찾을 수 없음: ID {}", memberId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
            }

            try {
                MBTI mbti = request.getMbti();

                Member updatedMember = memberCommandService.updateMemberMbti(memberId, mbti);

                MemberDetailResDto memberInfo = memberQueryService.getMemberInfo(memberId);

                return ResponseEntity.ok(memberInfo);
            } catch (IllegalArgumentException e) {
            log.error("유효하지 않은 MBTI 유형: {}", request.getMbti());
            return ResponseEntity.badRequest().body("유효하지 않은 MBTI 유형입니다. 올바른 MBTI 유형을 입력해주세요.");
            }
        } catch (Exception e) {
            log.error("회원 MBTI 수정 중 오류 발생: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원 MBTI 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

}
