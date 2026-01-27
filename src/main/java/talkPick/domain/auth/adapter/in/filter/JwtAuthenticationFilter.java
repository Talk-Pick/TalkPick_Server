package talkPick.domain.auth.adapter.in.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.core.common.exception.handler.SecurityExceptionHandler;
import talkPick.core.auth.constants.AuthConstants;
import talkPick.domain.auth.domain.AuthenticatedMember;
import talkPick.domain.auth.port.out.TokenParserPort;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static talkPick.core.common.exception.ErrorCode.UNAUTHORIZED;
import static talkPick.core.auth.config.SecurityWhiteList.PATHS;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenParserPort tokenParserPort;

    private static final List<AntPathRequestMatcher> WHITE_MATCHERS =
            Arrays.stream(PATHS).map(AntPathRequestMatcher::new).toList();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (isWhiteListPath(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = extractToken(request);
        if (!tokenParserPort.validateToken(accessToken)) {
            throw new SecurityExceptionHandler(UNAUTHORIZED);
        }

        doAuthentication(accessToken);
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteListPath(HttpServletRequest request) {
        return WHITE_MATCHERS.stream().anyMatch(matcher -> matcher.matches(request));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(header) || !header.startsWith(AuthConstants.BEARER)) {
            throw new SecurityExceptionHandler(UNAUTHORIZED);
        }
        return header.substring(AuthConstants.BEARER.length());
    }

    private void doAuthentication(String token) {
        Long memberId = tokenParserPort.getMemberIdFromToken(token);
        String role = tokenParserPort.getRoleFromToken(token);
        var authentication = AuthenticatedMember.of(token, memberId, role);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}