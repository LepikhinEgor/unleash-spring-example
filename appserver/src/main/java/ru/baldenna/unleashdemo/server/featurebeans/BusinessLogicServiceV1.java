package ru.baldenna.unleashdemo.server.featurebeans;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ru.baldenna.unleashdemo.server.featurebeans.BusinessLogicServiceV1.BUSINESS_LOGIC_SERVICE_V_1;

@Service(BUSINESS_LOGIC_SERVICE_V_1)
public class BusinessLogicServiceV1 implements BusinessLogicService {

    public static final String BUSINESS_LOGIC_SERVICE_V_1 = "businessLogicServiceV1";

    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public void doSomething() {
        Counter.builder("feature.status")
                .tags("status", "disabled")
                .tags("feature", "beans-feature")
                .register(meterRegistry)
                .increment();
    }
}
