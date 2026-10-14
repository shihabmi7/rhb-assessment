package com.shihab.rhbaccess.logging;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class RequestResponseLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingAspect.class);

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        String endpoint = describeRequest();
        log.info("REQUEST  {} -> {} args={}", endpoint, pjp.getSignature().toShortString(),
                Arrays.toString(pjp.getArgs()));
        long start = System.currentTimeMillis();
        try {
            Object result = pjp.proceed();
            log.info("RESPONSE {} ({} ms) body={}", endpoint, System.currentTimeMillis() - start, result);
            return result;
        } catch (Throwable ex) {
            log.warn("ERROR    {} ({} ms) {}: {}", endpoint, System.currentTimeMillis() - start,
                    ex.getClass().getSimpleName(), ex.getMessage());
            throw ex;
        }
    }

    private String describeRequest() {
        if (RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attrs) {
            HttpServletRequest req = attrs.getRequest();
            String query = req.getQueryString() == null ? "" : "?" + req.getQueryString();
            return req.getMethod() + " " + req.getRequestURI() + query;
        }
        return "n/a";
    }
}
