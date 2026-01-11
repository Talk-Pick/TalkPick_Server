package talkPick.external.apple.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import talkPick.domain.member.adapter.in.dto.MemberReqDto;
import talkPick.domain.member.converter.MemberConverter;
import talkPick.domain.member.dto.MemberDataDto;
import talkPick.external.apple.port.in.AppleOidcUsecase;
import talkPick.global.exception.ErrorCode;
import talkPick.global.exception.handler.AppleHandler;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.net.URL;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppleOidcService implements AppleOidcUsecase {

    // Apple JWKS
    private static final String JWK_URL = "https://appleid.apple.com/auth/keys";
    // iss 검증 값
    private static final String ISSUER = "https://appleid.apple.com";
    private static final String BUNDLE_ID = "io.tuist.TalkPick";

    @Override
    public MemberDataDto.MemberData verifyAndParseIdToken(MemberReqDto.OAuth2LoginRequest request) {
        try {
            String identityToken = request.getIdToken();
            String[] parts = identityToken.split("\\.");
            if (parts.length != 3) throw new AppleHandler(ErrorCode.INVALID_JWT_TOKEN);

            String headerJson = new String(Base64.getUrlDecoder().decode(parts[0]));
            ObjectMapper mapper = new ObjectMapper();
            JsonNode header = mapper.readTree(headerJson);
            String kid = header.get("kid").asText();


            JsonNode keys = mapper.readTree(new URL(JWK_URL)).get("keys");
            JsonNode matchedKey = null;
            for (JsonNode key : keys) {
                if (key.get("kid").asText().equals(kid)) {
                    matchedKey = key;
                    break;
                }
            }
            if (matchedKey == null) {
                log.error("Apple Matching Key not found for kid: {}", kid);
                throw new AppleHandler(ErrorCode.ERROR_ON_VERIFYING, "애플 공개키 목록에서 kid가 일치하는 키를 찾을 수 없습니다. kid: " + kid);
            }

            String n = matchedKey.get("n").asText();
            String e = matchedKey.get("e").asText();
            BigInteger modulus = new BigInteger(1, Base64.getUrlDecoder().decode(n));
            BigInteger exponent = new BigInteger(1, Base64.getUrlDecoder().decode(e));
            RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
            RSAPublicKey publicKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);


            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .requireIssuer(ISSUER)
                    .setAllowedClockSkewSeconds(300)
                    .build();

            Claims claims = parser.parseClaimsJws(identityToken).getBody();

            Object audObj = claims.get("aud");
            boolean audOk = false;
            log.info("Apple BUNDLE_ID: {}, Token aud: {}", BUNDLE_ID, audObj);
            
            if (audObj instanceof String audStr) {
                audOk = BUNDLE_ID.equals(audStr);
            } else if (audObj instanceof List<?> audList) {
                audOk = audList.stream().anyMatch(a -> BUNDLE_ID.equals(String.valueOf(a)));
            }
            
            if (!audOk) {
                log.error("Apple Audience mismatch. Expected: {}, Received: {}", BUNDLE_ID, audObj);
                throw new AppleHandler(ErrorCode.INVALID_JWT_TOKEN, "애플 토큰의 aud(Audience)가 일치하지 않습니다. 기대값: " + BUNDLE_ID + ", 실제값: " + audObj);
            }

            return MemberConverter.toAppleMemberData(claims);

        } catch (ExpiredJwtException e) {
            log.error("Apple Token Expired", e);
            throw new AppleHandler(ErrorCode.EXPIRED_JWT_TOKEN, "애플 토큰이 만료되었습니다: " + e.getMessage());
        } catch (Exception e) {
            log.error("Apple OAuth Error: {}", e.getMessage(), e);
            throw new AppleHandler(ErrorCode.ERROR_ON_VERIFYING, "애플 토큰 검증 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}