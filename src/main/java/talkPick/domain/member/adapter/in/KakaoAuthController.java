package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.admin.domain.type.Role;
import talkPick.domain.member.adapter.in.dto.KakaoTokenResponse;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.KakaoOAuthHandler;
import talkPick.domain.member.application.MemberCommandService;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;
import talkPick.global.security.jwt.dto.JwtResDTO;
import talkPick.global.security.jwt.util.JwtProvider;

import java.io.IOException;
import java.util.Optional;

@RestController
@Slf4j
public class KakaoAuthController {

    @Autowired
    MemberQueryService memberQueryService;

    @Autowired
    MemberCommandService memberCommandService;

    @Autowired
    private JwtProvider jwtProvider; // JwtProvider 주입

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.response-type}")
    private String responseType;

    @Value("${app.secure-cookie:false}")
    private boolean secureCookie;


    //카카오 회원가입
    @GetMapping("/api/v1/oauth/kakao/authorize")
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

    //인가코드 받고 토큰 요청하는 api
    @GetMapping("/api/v1/topic/kakao")
    public void processCallback(@RequestParam("code") String code,
                                HttpServletRequest request,
                                HttpServletResponse response) throws IOException {
        log.info("카카오 인가코드 수신: {}", code);

        KakaoTokenResponse tokenResponse = KakaoOAuthHandler.requestToken(code, clientId, redirectUri);
        log.info("토큰 발급 결과: {}", tokenResponse);

        KakaoUserInfo userInfo = KakaoOAuthHandler.requestUserInfo(tokenResponse.getAccess_token());
        log.info("카카오 사용자 정보 수신: {}", userInfo);

        HttpSession session = request.getSession(true);
        session.setAttribute("userInfo", userInfo);
        log.info("세션에 저장된 사용자 정보: {}", session.getAttribute("userInfo"));

        Optional<Member> existingMemberOpt = memberQueryService.findByKakaoId(userInfo.getId());
        if (existingMemberOpt.isPresent()) {
            Member existingMember = existingMemberOpt.get();
            log.info("기존 회원 정보: {}", existingMember);

            JwtResDTO.Login jwtToken = jwtProvider.createJwt(
                    existingMember.getId(),
                    String.valueOf(Role.MEMBER)
            );
            jwtProvider.addTokenCookies(response, jwtToken);
            session.removeAttribute("userInfo");
            log.info("기존 회원 - 세션 정보 삭제 후 리다이렉트");
            response.sendRedirect("/api/v1/topic");
        } else {
            log.info("신규 회원 - 추가 정보 입력 페이지로 리다이렉트");
            response.sendRedirect("/api/v1/topic/additional");
        }
    }


    @PostMapping("/api/v1/topic/additional")
    public void setMBTI(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam("mbti") String mbtiValue) throws IOException {
        log.info("MBTI 추가 정보 처리 시작: 입력된 MBTI 값 {}", mbtiValue);

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userInfo") == null) {
            log.error("세션이 만료되었거나 카카오 사용자 정보가 없습니다.");
            response.sendRedirect("/oauth/kakao/authorize");
            return;
        }

        KakaoUserInfo userInfo = (KakaoUserInfo) session.getAttribute("userInfo");
        log.info("세션에서 조회된 사용자 정보: {}", userInfo);

        MBTI mbti;
        try {
            mbti = MBTI.valueOf(mbtiValue.toUpperCase());
        } catch (IllegalArgumentException e) {
            mbti = MBTI.ENFJ; // 기본값
            log.warn("MBTI 값이 잘못 입력됨: {}, 기본 MBTI {}로 설정", mbtiValue, mbti);
        }

        Member member = new Member(userInfo, mbti);
        log.info("새로운 회원 정보 생성: {}", member);

        memberCommandService.setKakaoMember(member);

        JwtResDTO.Login jwtToken = jwtProvider.createJwt(
                member.getId(),
                String.valueOf(Role.MEMBER)
        );
        log.info("JWT 생성 완료: {}", jwtToken);

        jwtProvider.addTokenCookies(response, jwtToken);
        session.removeAttribute("userInfo");
        log.info("사용자 세션 정보 삭제 및 리다이렉션");
        response.sendRedirect("/api/v1/topic");
    }


    //로그인 이후에 화면 (홈화면 연결)
    @GetMapping("/api/v1/topic")
    public MemberKakaoResDTO getUserInfoFromSession(HttpServletRequest request
    ) {
        String accessToken = getAccessTokenFromCookies(request);

        if (accessToken == null) {
            log.error("액세스 토큰이 없습니다.");
            return null;
        }

        // 토큰에서 사용자 ID 추출
        Long memberId = jwtProvider.getUserIdFromToken(accessToken);

        // 사용자 정보 조회
        Optional<Member> memberOpt = memberQueryService.findById(memberId);

        return memberOpt.map(MemberKakaoResDTO::new).orElse(null);

    }

    private String getAccessTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("access_token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}

