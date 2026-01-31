package talkPick.core.rateLimiter.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import talkPick.core.common.exception.ErrorCode;
import talkPick.core.common.exception.handler.RateLimitExceededExceptionHandler;
import talkPick.core.rateLimiter.port.RateLimiterManager;

@Aspect
@Component("CustomRateLimiterAspect")
@RequiredArgsConstructor
public class RateLimiterAspect {
    private final RateLimiterManager rateLimiterManager;

    @Around("@annotation(talkPick.core.rateLimiter.annotation.RateLimited)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        if (rateLimiterManager.tryConsume(getIpAddress(request), getUri(request))) {
            return joinPoint.proceed();
        } else {
            throw new RateLimitExceededExceptionHandler(ErrorCode.RATE_LIMIT_EXCEEDED);
        }
    }

    private static String getIpAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
    private static String getUri(HttpServletRequest request) {
        return request.getRequestURI();
    }
}