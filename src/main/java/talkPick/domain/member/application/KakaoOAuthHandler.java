package talkPick.domain.member.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import talkPick.domain.member.adapter.in.dto.KakaoTokenResponse;
import talkPick.domain.member.adapter.in.dto.KakaoUserInfo;
import talkPick.batch.topic.exception.KaKaoOAuthException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

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
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // POST 요청 본문은 비어 있을 수 있음
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);

        RestTemplate restTemplate = new RestTemplate();

        // 메시지 컨버터 설정
        for (HttpMessageConverter<?> converter : restTemplate.getMessageConverters()) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter jsonConverter = (MappingJackson2HttpMessageConverter) converter;
                jsonConverter.setSupportedMediaTypes(Arrays.asList(
                        MediaType.APPLICATION_JSON,
                        MediaType.valueOf("application/json;charset=UTF-8")
                ));
            }
        }

        try {
            // POST 메서드 사용
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    userInfoUrl,
                    HttpMethod.POST,  // GET에서 POST로 변경
                    entity,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            log.info("카카오 사용자 정보 원본 응답: {}", response.getBody());

            // Map에서 KakaoUserInfo 객체 생성
            KakaoUserInfo userInfo = new KakaoUserInfo();
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null) {
                // ID 설정
                if (responseBody.get("id") != null) {
                    userInfo.setId(responseBody.get("id").toString());
                }

                // connected_at 설정
                if (responseBody.get("connected_at") != null) {
                    userInfo.setConnected_at(responseBody.get("connected_at").toString());
                }

                // properties 설정
                if (responseBody.get("properties") != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> properties = (Map<String, Object>) responseBody.get("properties");
                    userInfo.setProperties(properties);
                }

                // kakao_account 설정
                if (responseBody.get("kakao_account") != null) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> kakaoAccount = (Map<String, Object>) responseBody.get("kakao_account");
                    userInfo.setKakao_account(kakaoAccount);
                }
            }

            log.info("변환된 카카오 사용자 정보: {}", userInfo);
            return userInfo;
        } catch (HttpClientErrorException e) {
            log.error("카카오 API 클라이언트 오류: {}, 응답: {}", e.getMessage(), e.getResponseBodyAsString(), e);
            throw new KaKaoOAuthException("카카오 인증 과정에서 오류가 발생했습니다: " + e.getMessage(), e);
        } catch (HttpServerErrorException e) {
            log.error("카카오 API 서버 오류: {}, 응답: {}", e.getMessage(), e.getResponseBodyAsString(), e);
            throw new KaKaoOAuthException("카카오 서버에 문제가 발생했습니다. 잠시 후 다시 시도하세요: " + e.getMessage(), e);
        } catch (Exception e) {
            log.error("카카오 사용자 정보 요청 실패: {}", e.getMessage(), e);
            throw new KaKaoOAuthException("카카오 사용자 정보 요청 중 오류 발생: " + e.getMessage(), e);
        }
    }



}
