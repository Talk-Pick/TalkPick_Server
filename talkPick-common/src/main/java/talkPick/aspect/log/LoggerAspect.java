package talkPick.aspect.log;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggerAspect {
//    @Before("execution(* org.tictoc.tictoc.domain.*.*(..))")
//    public void before(JoinPoint joinPoint) {
//        log.info("[START] | where: {}", joinPoint.toString());
//    }
//
//    @AfterReturning("execution(* org.tictoc.tictoc.domain.*.*(..))")
//    public void afterReturning(JoinPoint joinPoint) {
//        log.info("[END] | where: {}", joinPoint.toString());
//    }
//
//    @AfterThrowing(value = "execution(* org.tictoc.tictoc.domain.*.*(..))", throwing = "exception")
//    public void afterThrowing(JoinPoint joinPoint, Exception exception) {
//        log.error("[END_WITH_FAIL] | where: {}", joinPoint.toString());
//        log.error("[END_WITH_FAIL] | exception: [ {} ]", exception, exception);
//    }
}