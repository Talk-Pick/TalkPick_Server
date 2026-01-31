package talkPick.domain.auth.adapter.in.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.TalkPickException;
import talkPick.core.common.response.ApiResponse;
import talkPick.core.auth.constants.AuthConstants;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TalkPickException e) {
            handleException(response, e.getErrorCode());
        } catch (Exception e) {
            log.error("[ExceptionHandlerFilter] {}", e.getMessage(), e);
            handleException(response, ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void handleException(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(AuthConstants.CHARACTER_TYPE);
        response.setStatus(errorCode.getStatus().value());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.ofErrorCode(errorCode)));
    }
}