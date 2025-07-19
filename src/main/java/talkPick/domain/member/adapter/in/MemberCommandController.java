package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import talkPick.domain.admin.domain.type.Role;
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
import talkPick.global.util.CookieUtil;

import java.io.IOException;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * 회원 명령 관련 컨트롤러
 * 회원 가입, MBTI 설정 등의 기능을 제공합니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class MemberCommandController implements MemberCommandApi {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    /**
     * 이메일 기반 회원가입을 처리합니다.
     */
    @Operation(summary = "이메일 회원가입", description = "이메일, 비밀번호 등으로 회원가입을 처리합니다.")
    @Tag(name = "회원가입 API", description = "회원가입(카카오/이메일) 관련 API")
    @PostMapping("/members/join")
    public ResponseEntity<?> joinEmailMember(
            @Parameter(description = "회원가입 요청 DTO", required = true)
            @RequestBody MemberEmailReqDto memberReqDto,
            HttpServletResponse response) {
        log.info("이메일 회원가입 요청: {}", memberReqDto.getEmail());

        // 이메일 중복 확인
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

                // JWT 토큰 생성 및 쿠키 설정
                JwtResDTO.Login jwtToken = jwtProvider.createJwt(member.getId(), String.valueOf(Role.MEMBER));
                jwtProvider.addTokenCookies(response, jwtToken);
                log.info("JWT 생성 완료: {}", jwtToken);
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

    /**
     * MBTI 입력 페이지를 표시합니다.
     */
    @Operation(summary = "카카오 회원가입 - MBTI 입력 페이지 리다이렉트", description = "카카오 신규 회원이 MBTI를 입력할 수 있는 페이지로 리다이렉트합니다.")
    @Tag(name = "회원가입 API", description = "회원가입(카카오/이메일) 관련 API")
    @GetMapping("/members/additional")
    public void showMbtiForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("MBTI 입력 페이지 요청");
        
        // 쿠키에서 액세스 토큰 또는 카카오 ID 확인
        String accessToken = cookieUtil.getCookieValue(request, "access_token");
        String kakaoId = cookieUtil.getCookieValue(request, "kakao_id");
        
        // 액세스 토큰이나 카카오 ID 중 하나라도 있으면 인증된 것으로 간주
        if (accessToken == null && kakaoId == null) {
            log.error("인증 정보가 없습니다. 카카오 로그인 페이지로 리다이렉트합니다.");
            response.sendRedirect("/api/v1/oauth/kakao/authorize");
            return;
        }
        
        // 액세스 토큰이 있는 경우 유효성 검증
        if (accessToken != null) {
            try {
                // 토큰 유효성 검증 (예외가 발생하지 않으면 유효한 토큰)
                jwtProvider.getUserIdFromToken(accessToken);
            } catch (Exception e) {
                log.error("유효하지 않은 토큰입니다: {}", e.getMessage());
                response.sendRedirect("/api/v1/oauth/kakao/authorize");
                return;
            }
        }
        
        // 인증 정보가 유효하므로 MBTI 입력 페이지로 리다이렉트
        log.info("인증 정보 확인 완료. MBTI 입력 페이지로 리다이렉트합니다.");
        response.sendRedirect("/mbti-form.html");
    }

    /**
     * 회원의 MBTI를 수정합니다.
     */
    @Operation(summary = "회원 MBTI 수정", description = "회원이 자신의 MBTI를 수정합니다.")
    @Tag(name = "회원 명령 API", description = "회원 정보 수정 등 회원 관련 명령 API")
    @PutMapping("/members/mbti")
    public ResponseEntity<?> updateMemberMbti(
            @Parameter(description = "MBTI 수정 요청 DTO", required = true)
            @RequestBody MemberMbtiUpdateRequestDto request) {
        log.info("회원 MBTI 수정 요청 - MBTI: {}", request.getMbti());
        
        try {
            // 현재 인증된 사용자 정보 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            long memberId = Long.parseLong(authentication.getName());

            // 회원 정보 조회
            Optional<Member> memberOpt = memberQueryService.findById(memberId);
            if (memberOpt.isEmpty()) {
                log.error("회원을 찾을 수 없음: ID {}", memberId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보를 찾을 수 없습니다.");
            }

            try {
                // MBTI 값 처리
                MBTI mbti = request.getMbti();
                
                // MBTI 업데이트
                Member updatedMember = memberCommandService.updateMemberMbti(memberId, mbti);
                
                // 업데이트된 회원 정보 조회
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