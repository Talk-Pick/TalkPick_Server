package talkPick.domain.member.application;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import talkPick.domain.member.adapter.in.dto.KakaoTokenResponse;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.domain.member.exception.KaKaoOAuthException;

@Slf4j
public class KakaoOAuthHandler {

    // 토큰 요청 메서드
    public static KakaoTokenResponse requestToken(String code, String clientId, String redirectUri) {
        log.info("카카오 토큰 요청 시작");
        String tokenUrl = "https://kauth.kakao.com/oauth/token";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", clientId);
            params.add("redirect_uri", redirectUri);
            params.add("code", code);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<KakaoTokenResponse> response = restTemplate.postForEntity(
                    tokenUrl,
                    request,
                    KakaoTokenResponse.class
            );
            log.info("카카오 토큰 발행 완료: {}", response.getStatusCode());
            return response.getBody();
        } catch (HttpClientErrorException e) {
            // 클라이언트 오류 (4xx)
            log.info("카카오 API 클라이언트 오류: ", e);
            throw new KaKaoOAuthException("카카오 인증 과정에서 오류가 발생했습니다.", e);
        } catch (HttpServerErrorException e) {
            // 서버 오류 (5xx)
            log.info("카카오 API 서버 오류: ", e);
            throw new KaKaoOAuthException("카카오 서버에 문제가 발생했습니다. 잠시 후 다시 시도하세요.", e);
        } catch (Exception e) {
            log.info("카카오 토큰 요청 중 예상치 못한 오류: ", e);
            throw new KaKaoOAuthException("인증 과정에서 오류가 발생했습니다.", e);
        }

    }

    // 사용자 정보 요청 메서드
    public static KakaoUserInfo requestUserInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<KakaoUserInfo> response = restTemplate.postForEntity(
                userInfoUrl,
                request,
                KakaoUserInfo.class
        );

        System.out.println("사용자 정보 요청 성공: " + response.getBody());
        return response.getBody();
    }
}
