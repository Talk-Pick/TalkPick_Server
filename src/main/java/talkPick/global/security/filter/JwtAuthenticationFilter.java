package talkPick.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.global.exception.TalkPickException;
import talkPick.global.exception.handler.SecurityExceptionHandler;
import talkPick.global.response.ApiResponse;
import talkPick.global.security.constants.AuthConstants;
import talkPick.global.security.jwt.port.in.RedisCommandUseCase;
import talkPick.global.security.jwt.util.JwtProvider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static talkPick.global.exception.ErrorCode.UNAUTHORIZED;
import static talkPick.global.security.model.WhiteList.PATHS;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;
    private final RedisCommandUseCase redisCommandUseCase;

    private static final List<AntPathRequestMatcher> whiteMatchers =
            Arrays.stream(PATHS).map(AntPathRequestMatcher::new).toList();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (whiteMatchers.stream().anyMatch(matcher -> matcher.matches(request))) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final var authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith(AuthConstants.BEARER)) {
                throw new SecurityExceptionHandler(UNAUTHORIZED);
            }

            // Bearer 토큰을 블랙리스트에서 체크
            if (redisCommandUseCase.isTokenBlacklisted(authorizationHeader)) {
                throw new SecurityExceptionHandler(UNAUTHORIZED);
            }

            final var accessToken = authorizationHeader.substring(AuthConstants.BEARER.length());
            final var memberId = jwtProvider.getMemberIdFromToken(accessToken);
            final var role = jwtProvider.getRoleFromToken(accessToken);
            doAuthentication(accessToken, memberId, role);

            filterChain.doFilter(request, response);
        } catch (TalkPickException e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(e.getErrorCode().getStatus().value());

            response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.ofErrorCode(e.getErrorCode())));
        }
    }



    private void doAuthentication(final String token, final Long memberId, final String role) {
        var tokenAuthentication = TokenAuthentication.createTokenAuthentication(token, memberId, role);
        var securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(tokenAuthentication);
    }
}