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
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.domain.admin.adapter.out.repository.AdminJpaRepository;
import talkPick.domain.member.adapter.out.repository.MemberJpaRepository;
import talkPick.global.common.constants.auth.AuthConstants;
import talkPick.global.error.ErrorCode;
import talkPick.global.error.exception.admin.AdminException;
import talkPick.global.error.exception.auth.UnauthorizedException;
import talkPick.global.security.jwt.util.JwtProvider;
import talkPick.global.security.util.CustomUserDetails;
import talkPick.domain.admin.domain.Admin;
import talkPick.domain.member.domain.Member;
import talkPick.global.error.exception.auth.AuthException;
import talkPick.global.error.exception.member.MemberNotFoundException;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final AdminJpaRepository adminJpaRepository;
    private final MemberJpaRepository memberJpaRepository;

    private static final List<String> whiteList = List.of(
            "/api/v1/admin/signup",
            "/api/v1/admin/login",
            "/api/v1/member/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();

        if (whiteList.contains(path)) { // JWT 인증 X
            filterChain.doFilter(request, response);
            return;
        }

        final var accessToken = getAccessToken(request);
        final var userId = jwtProvider.getUserIdFromToken(accessToken);
        final var role = jwtProvider.getRoleFromToken(accessToken);
        doAuthentication(userId, role);
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
            Admin admin = adminJpaRepository.findById(userId)
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
