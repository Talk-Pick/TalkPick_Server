package talkPick.domain.member.adapter.in;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkPick.domain.member.adapter.in.dto.KakaoTokenResponse;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.adapter.out.dto.MemberKakaoResDTO;
import talkPick.domain.member.application.KakaoOAuthHandler;
import talkPick.domain.member.application.MemberQueryService;
import talkPick.domain.member.domain.Member;
import talkPick.domain.member.domain.type.MBTI;

import java.io.IOException;

@RestController
public class KakaoAuthController {

    @Autowired
    MemberQueryService memberQueryService;

    @Value("${kakao.client-id}")
    private String clientId;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @Value("${kakao.response-type}")
    private String responseType;

    //카카오 회원가입
    @GetMapping("/oauth/kakao/authorize")
    public void joinKakaoMember(HttpServletResponse response) throws IOException {
        // 카카오 로그인 URL 생성
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=" + responseType;

        System.out.println("카카오 로그인 요청 URL: " + kakaoAuthUrl);

        // 리다이렉트 된 페이지로 이동
        response.sendRedirect(kakaoAuthUrl);
    }

    //인가코드 받고 토큰 요청하는 api
    @GetMapping("/api/v1/topic/kakao")
    public void processCallback(@RequestParam("code") String code,
                                             HttpServletRequest request,
                                             HttpServletResponse response) throws IOException {
        System.out.println("인가코드 : " + code);
        // 인가 코드로 토큰 요청
        KakaoTokenResponse tokenResponse = KakaoOAuthHandler.requestToken(code, clientId, redirectUri);
        System.out.println("토큰 발행 요청 : " + tokenResponse);

        System.out.println("사용자 정보 요청");
        // 사용자 정보 요청
        KakaoUserInfo userInfo = KakaoOAuthHandler.requestUserInfo(tokenResponse.getAccess_token());

        // 세션에 사용자 정보 저장
        HttpSession session = request.getSession();
        session.setAttribute("userInfo", userInfo);

        // /api/v1/topic으로 리다이렉트되기 전에, MBTI 입력 페이지를 거치도록 수정.
        response.sendRedirect("/api/v1/topic/additional-info");

    }

    @GetMapping("/api/v1/topic/additional-info")
    public void setMBTI(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("사용자 mbti 저장 페이지");
        HttpSession session = request.getSession();
        KakaoUserInfo userInfo = (KakaoUserInfo) session.getAttribute("userInfo");
        Member member = new Member(userInfo, MBTI.ENFJ);
        memberQueryService.setKakaoMember(member);

        session.setAttribute("userInfo", member);

        response.sendRedirect("/api/v1/topic");
    }

    //로그인 이후에 화면 (사용자 정보 추가로 제공 받아야 함)
    @GetMapping("/api/v1/topic")
    public MemberKakaoResDTO getUserInfoFromSession(HttpSession session) {
        MemberKakaoResDTO userInfo = new MemberKakaoResDTO((Member) session.getAttribute("userInfo"));
        return userInfo;
    }
}
