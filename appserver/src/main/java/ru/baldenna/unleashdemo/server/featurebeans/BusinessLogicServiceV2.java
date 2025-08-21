package ru.baldenna.unleashdemo.server.featurebeans;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ru.baldenna.unleashdemo.server.featurebeans.BusinessLogicServiceV2.BUSINESS_LOGIC_SERVICE_V_2;

@Service(BUSINESS_LOGIC_SERVICE_V_2)
public class BusinessLogicServiceV2 implements BusinessLogicService {

    public static final String BUSINESS_LOGIC_SERVICE_V_2 = "businessLogicServiceV2";

    @Autowired
    MeterRegistry meterRegistry;

    @Override
    public void doSomething() {
        Counter.builder("feature.status")
                .tags("status", "enabled")
                .tags("feature", "beans-feature")
                .register(meterRegistry)
                .increment();
    }
}
