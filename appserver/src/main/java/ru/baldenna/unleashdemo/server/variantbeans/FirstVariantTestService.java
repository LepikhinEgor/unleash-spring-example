package ru.baldenna.unleashdemo.server.variantbeans;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ru.baldenna.unleashdemo.server.variantbeans.FirstVariantTestService.FIRST_VARIANT_SERVICE_BEAN;

@Service(FIRST_VARIANT_SERVICE_BEAN)
public class FirstVariantTestService implements VariantTestService {

    public static final String FIRST_VARIANT_SERVICE_BEAN = "firstVariantTestService";


    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public void doSomething() {
        Counter.builder("feature.variant")
                .tags("variant", "first")
                .register(meterRegistry)
                .increment();
    }
}
