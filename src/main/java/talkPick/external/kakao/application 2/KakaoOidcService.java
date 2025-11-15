package talkPick.external.kakao.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.external.kakao.port.in.KakaoOidcUsecase;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.KakaoExceptionHandler;

import java.math.BigInteger;
import java.net.URL;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

@Component
@Slf4j
@RequiredArgsConstructor
public class KakaoOidcService implements KakaoOidcUsecase {
    // Kakao 공개키 -> Kakao 가 서명한 JWT(id_token) 을 검증하기 위해선 공개키(JWK) 필요
    private static final String JWK_URL = "https://kauth.kakao.com/.well-known/jwks.json";
    // ISSER 상수 -> id_token 안에 들어 있는 iss claim과 비교해 정품 Kakao 토큰인지 검증
    private static final String ISSUER = "https://kauth.kakao.com";

    /**
     * id_token을 검증하고 담겨있는 멤버 데이터 추출하는 메서드
     */
    @Override
    public MemberDataDto.KakaoMemberData verifyAndParseIdToken(MemberReqDto.KakaoOAuth2LoginRequest request) {
        try {
            // JWT 디코드 (헤더 추출)
            String[] parts = request.getIdToken().split("\\.");
            if (parts.length != 3) throw new KakaoExceptionHandler(ErrorCode.INVALID_JWT_TOKEN);

            // 헤더 에서 kid(키 아이디) 추출
            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.get("kid").asText();

            // JWKS 불러오기
            JsonNode keys = mapper.readTree(new URL(JWK_URL)).get("keys");

            JsonNode matchedKey = null;
            for (JsonNode key : keys) {
                if (key.get("kid").asText().equals(kid)) {
                    matchedKey = key;
                    break;
                }
            }

            if (matchedKey == null) throw new KakaoExceptionHandler(ErrorCode.INVALID_JWT_TOKEN);

            // 공개키 추출 (n, e -> RSA public Key)
            String n = matchedKey.get("n").asText();
            String e = matchedKey.get("e").asText();
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));

            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);

            // 검증 및 Claims 추출
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .requireIssuer(ISSUER)
                    .build();

            Claims claims = parser.parseClaimsJws(request.getIdToken()).getBody();

            return MemberConverter.toKakaoMemberData(claims);
        } catch (Exception e) {
            log.error("KakaoOidcService Error Occurred: {}", e.getMessage());
            throw new KakaoExceptionHandler(ErrorCode.ERROR_ON_VERIFYING);
        }
    }
}
