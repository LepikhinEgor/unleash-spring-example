package ru.baldenna.unleashdemo.server.variantbeans;

import io.getunleash.Unleash;
import io.getunleash.variant.Payload;
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

    @Autowired
    Unleash unleash;

    @Override
    public void doSomething() {
        var payload = unleash.getVariant("variants-feature").getPayload().map(Payload::getValue).orElse("empty");
        Counter.builder("feature.variant")
                .tags("variant", "first", "payload", payload)
                .register(meterRegistry)
                .increment();
    }
}
