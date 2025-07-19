package talkPick.global.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.domain.admin.adapter.out.repository.AdminJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.global.security.constants.AuthConstants;
import talkPick.global.exception.ErrorCode;
import talkPick.domain.admin.exception.AdminException;
import talkPick.global.security.exception.UnauthorizedException;
import talkPick.global.security.jwt.util.JwtProvider;
import talkPick.global.security.model.CustomUserDetails;
import talkPick.domain.member.domain.Member;
import talkPick.global.security.exception.AuthException;
import talkPick.domain.member.exception.MemberNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static talkPick.global.security.model.WhiteList.PATHS;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AdminJpaRepository adminJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    private static final List<AntPathRequestMatcher> whiteMatchers =
            Arrays.stream(PATHS).map(AntPathRequestMatcher::new).toList();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (whiteMatchers.stream().anyMatch(matcher -> matcher.matches(request))) {
            filterChain.doFilter(request, response);
            return;
        }

        final var accessToken = getAccessToken(request);
        doAuthentication(jwtProvider.getUserIdFromToken(accessToken), jwtProvider.getRoleFromToken(accessToken));
        filterChain.doFilter(request, response);
    }

    private String getAccessToken(final HttpServletRequest request) {
        final var accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(accessToken) && accessToken.startsWith(AuthConstants.BEARER)) {
            return accessToken.substring(AuthConstants.BEARER.length());
        }
        throw new UnauthorizedException(ErrorCode.UNAUTHORIZED);
    }

    private void doAuthentication(final Long userId, final String role) {
        UserDetails userDetails;

        if ("ADMIN".equalsIgnoreCase(role)) {
            var admin = adminJpaRepository.findById(userId)
                    .orElseThrow(() -> new AdminException(ErrorCode.ADMIN_NOT_FOUND));
            userDetails = new CustomUserDetails(admin);
        } else if ("USER".equalsIgnoreCase(role)) {
            Member member = memberJpaRepository.findById(userId)
                    .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));
            userDetails = new CustomUserDetails(member);
        } else {
            throw new AuthException(ErrorCode.INVALID_ROLE);
        }

        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}