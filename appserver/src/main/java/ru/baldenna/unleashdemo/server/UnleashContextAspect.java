package ru.baldenna.unleashdemo.server;

import io.getunleash.UnleashContext;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.unleash.features.aop.UnleashContextThreadLocal;

@Component
@Aspect
public class UnleashContextAspect {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    ThreadLocalUnleashContextProvider unleashContextProvider;

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) "
            + "|| @within(org.springframework.stereotype.Controller)")
    public void restControllersPointcut() {
    }

    @Around("restControllersPointcut()")
    public Object aroundProcess(ProceedingJoinPoint jp) throws Throwable {
        String user = httpServletRequest.getParameter("user");
        String tariff = httpServletRequest.getParameter("tariff");

        unleashContextProvider.setContext(UnleashContext.builder()
                .userId(user)
                .addProperty("tariff", tariff)
                .build()
        );

        var result =  jp.proceed();
        unleashContextProvider.clearContext();
        return result;
    }
}
