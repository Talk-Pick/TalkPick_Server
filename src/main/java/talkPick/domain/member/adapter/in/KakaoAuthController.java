package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.adapter.in.dto.KakaoTokenResponse;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.KakaoOAuthHandler;
import talkPick.domain.member.application.MemberCommandService;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.domain.member.exception.KaKaoOAuthException;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;
import talkPick.global.util.CookieUtil;

import java.io.IOException;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
/**
 * 카카오 OAuth 인증 관련 컨트롤러
 * 카카오 로그인, 회원가입, 사용자 정보 조회 등의 기능을 제공합니다.
 */
@Tag(name = "회원가입 API", description = "회원가입(카카오/이메일) 관련 API")
@RestController
@RequestMapping("/api/v1/auth/kakao")
@RequiredArgsConstructor
@Slf4j
public class KakaoAuthController {

    private final MemberQueryService memberQueryService;
    private final MemberCommandService memberCommandService;
    private final JwtProvider jwtProvider;
    private final CookieUtil cookieUtil;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.response-type}")
    private String responseType;

    @Value("${app.secure-cookie:false}")
    private boolean secureCookie;

    /**
     * 카카오 회원가입 - 로그인 페이지 리다이렉트
     */
    @Operation(summary = "카카오 회원가입 - 로그인 페이지 리다이렉트", description = "카카오 회원가입을 시작하는 엔드포인트입니다. 호출 시 카카오 로그인 페이지로 리다이렉트됩니다.")
    @GetMapping("/authorize")
    public void joinKakaoMember(HttpServletResponse response) throws IOException {
        // 카카오 로그인 URL 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;

        log.info("카카오 로그인 요청 URL: {}", kakaoAuthUrl);

        // 리다이렉트 된 페이지로 이동
        response.sendRedirect(kakaoAuthUrl);
    }

    /**
     * 카카오 회원가입 - 인증 콜백
     * 인가 코드를 받아 토큰을 요청하고, 사용자 정보를 조회합니다.
     */
    @Operation(summary = "카카오 회원가입 - 인증 콜백", description = "카카오 인가 코드를 받아 토큰을 발급하고, 회원 여부에 따라 분기 처리합니다. code 파라미터는 카카오에서 리다이렉트된 후 쿼리스트링으로 전달됩니다.")
    @GetMapping("/callback")
    public void processCallback(
            @Parameter(description = "카카오 인가 코드", required = true, example = "abc123")
            @RequestParam("code") String code,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        log.info("카카오 인가코드 수신: {}", code);

        try {
            // 토큰 요청
            KakaoTokenResponse tokenResponse = KakaoOAuthHandler.requestToken(code, clientId, redirectUri);
            log.info("토큰 발급 결과: {}", tokenResponse);

            // 사용자 정보 요청
            KakaoUserInfo userInfo = KakaoOAuthHandler.requestUserInfo(tokenResponse.getAccess_token());
            log.info("카카오 사용자 정보 수신: {}", userInfo);

            // 기존 회원 확인
            Optional<Member> existingMemberOpt = memberQueryService.findByKakaoId(userInfo.getId());
            if (existingMemberOpt.isPresent()) {
                // 기존 회원인 경우 JWT 토큰 생성 및 쿠키 설정
                Member existingMember = existingMemberOpt.get();
                log.info("기존 회원 정보: {}", existingMember);

                JwtResDTO.Login jwtToken = jwtProvider.createJwt(
                        existingMember.getId(),
                        String.valueOf(Role.MEMBER)
                );
                jwtProvider.addTokenCookies(response, jwtToken);
                log.info("기존 회원 - JWT 토큰 생성 및 쿠키 설정 후 리다이렉트");
                response.sendRedirect("/api/v1/topic");
            } else {
                // 신규 회원인 경우 임시 정보 저장 및 MBTI 입력 페이지로 리다이렉트
                log.info("신규 회원 - 임시 정보 저장 및 MBTI 입력 페이지로 리다이렉트");

                // 카카오 ID 저장
                cookieUtil.addCookie(response, "kakao_id", userInfo.getId(),
                        "/", 1800, true, "Lax", secureCookie);

                // 닉네임 저장 (있는 경우)
                if (userInfo.getProperties() != null && userInfo.getProperties().get("nickname") != null) {
                    cookieUtil.addCookie(response, "kakao_nickname",
                            String.valueOf(userInfo.getProperties().get("nickname")),
                            "/", 1800, true, "Lax", secureCookie);
                }
                response.sendRedirect("/api/v1/members/additional");
            }
        } catch (KaKaoOAuthException e) {
            handleKakaoOAuthError(response, e);
        } catch (Exception e) {
            handleGenericError(response, e);
        }
    }

    /**
     * MBTI 추가 정보를 처리합니다.
     */
    @Operation(summary = "카카오 회원가입 - MBTI 추가 정보 입력", description = "신규 카카오 회원이 MBTI를 입력할 때 사용하는 API입니다. mbti 파라미터는 MBTI 유형(예: ENFP, ISTJ 등)을 입력합니다.")
    @PostMapping("/additional")
    public void setMBTI(HttpServletRequest request, HttpServletResponse response,
                        @Parameter(description = "MBTI 유형", required = true, example = "ENFP")
                        @RequestParam("mbti") String mbtiValue) throws IOException {
        log.info("MBTI 추가 정보 처리 시작: 입력된 MBTI 값 {}", mbtiValue);

        // 쿠키에서 카카오 ID와 닉네임 가져오기
        String kakaoId = cookieUtil.getCookieValue(request, "kakao_id");
        String nickname = cookieUtil.getCookieValue(request, "kakao_nickname");

        if (kakaoId == null) {
            log.error("카카오 ID 정보가 없습니다. 카카오 로그인 페이지로 리다이렉트합니다.");
            response.sendRedirect("/api/v1/auth/kakao/authorize");
            return;
        }

        log.info("쿠키에서 조회된 카카오 ID: {}, 닉네임: {}", kakaoId, nickname);

        // MBTI 값 처리
        MBTI mbti;
        try {
            mbti = MBTI.valueOf(mbtiValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            mbti = MBTI.ENFJ; // 기본값
            log.warn("MBTI 값이 잘못 입력됨: {}, 기본 MBTI {}로 설정", mbtiValue, mbti);
        }

        // 카카오 ID로 간소화된 KakaoUserInfo 객체 생성
        KakaoUserInfo userInfo = new KakaoUserInfo();
        userInfo.setId(kakaoId);
        if (nickname != null) {
            // 닉네임 정보가 있으면 properties에 추가
            userInfo.setProperties(java.util.Collections.singletonMap("nickname", nickname));
        }

        // 회원 생성 및 저장
        Member member = new Member(userInfo, mbti);
        log.info("새로운 회원 정보 생성: {}", member);
        memberCommandService.setKakaoMember(member);

        // JWT 토큰 생성 및 쿠키 설정
        JwtResDTO.Login jwtToken = jwtProvider.createJwt(
                member.getId(),
                String.valueOf(Role.MEMBER)
        );
        log.info("JWT 생성 완료: {}", jwtToken);
        jwtProvider.addTokenCookies(response, jwtToken);

        // 임시 쿠키 삭제
        cookieUtil.deleteCookie(response, "kakao_id", "/");
        cookieUtil.deleteCookie(response, "kakao_nickname", "/");
        log.info("임시 쿠키 삭제 및 리다이렉션");
        response.sendRedirect("/api/v1/topic");
    }

    /**
     * 카카오 OAuth 오류를 처리합니다.
     */
    private void handleKakaoOAuthError(HttpServletResponse response, KaKaoOAuthException e) throws IOException {
        log.error("카카오 OAuth 처리 중 오류 발생: {}", e.getMessage(), e);

        // 오류 메시지에 따라 다른 사용자 친화적인 메시지 제공
        String errorMessage;
        if (e.getMessage().contains("요청이 너무 많습니다")) {
            errorMessage = "현재 카카오 로그인 요청이 많아 처리가 지연되고 있습니다. 잠시 후 다시 시도해주세요.";
        } else if (e.getMessage().contains("카카오 서버에 문제가 발생")) {
            errorMessage = "카카오 서비스에 일시적인 문제가 발생했습니다. 잠시 후 다시 시도해주세요.";
        } else {
            errorMessage = "카카오 로그인 중 오류가 발생했습니다. 다시 시도해주세요.";
        }

        redirectToErrorPage(response, errorMessage);
    }

    /**
     * 일반 오류를 처리합니다.
     */
    private void handleGenericError(HttpServletResponse response, Exception e) throws IOException {
        log.error("카카오 로그인 처리 중 예상치 못한 오류 발생: {}", e.getMessage(), e);
        redirectToErrorPage(response, "로그인 처리 중 오류가 발생했습니다. 다시 시도해주세요.");
    }

    /**
     * 오류 페이지로 리다이렉트합니다.
     */
    private void redirectToErrorPage(HttpServletResponse response, String errorMessage) throws IOException {
        try {
            response.sendRedirect("/error?message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8"));
        } catch (java.io.UnsupportedEncodingException uee) {
            log.error("오류 메시지 인코딩 중 예외 발생", uee);
            response.sendRedirect("/error?message=로그인 처리 중 오류가 발생했습니다");
        }
    }
}