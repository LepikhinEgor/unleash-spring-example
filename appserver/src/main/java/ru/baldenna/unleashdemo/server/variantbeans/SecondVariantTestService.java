package ru.baldenna.unleashdemo.server.variantbeans;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ru.baldenna.unleashdemo.server.variantbeans.SecondVariantTestService.SECOND_VARIANT_SERVICE_BEAN;

@Service(SECOND_VARIANT_SERVICE_BEAN)
public class SecondVariantTestService implements VariantTestService {

    public static final String SECOND_VARIANT_SERVICE_BEAN = "secondVariantTestService";


    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public void doSomething() {
        Counter.builder("feature.variant")
                .tags("variant", "second")
                .register(meterRegistry)
                .increment();
    }
}
