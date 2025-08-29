package ru.baldenna.unleashdemo.server;

import io.getunleash.UnleashContext;
import io.getunleash.UnleashContextProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ThreadLocalUnleashContextProvider implements UnleashContextProvider {

    private final ThreadLocal<UnleashContext> currentContext = new ThreadLocal<>();

    @NotNull
    @Override
    public UnleashContext getContext() {
        return currentContext.get() != null ? currentContext.get() : UnleashContext.builder().build();
    }

    public void setContext(UnleashContext unleashContext) {
        currentContext.set(unleashContext);
    }

    public void clearContext() {
        currentContext.remove();
    }
}
