package ru.baldenna.unleashdemo.server;

import io.getunleash.Unleash;
import io.getunleash.UnleashContext;
import io.getunleash.UnleashContextProvider;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.baldenna.unleashdemo.server.featurebeans.BusinessLogicService;
import ru.baldenna.unleashdemo.server.variantbeans.VariantTestService;

import static ru.baldenna.unleashdemo.server.featurebeans.BusinessLogicServiceV1.BUSINESS_LOGIC_SERVICE_V_1;
import static ru.baldenna.unleashdemo.server.variantbeans.SecondVariantTestService.SECOND_VARIANT_SERVICE_BEAN;

@RestController
public class FeatureController {

    @Autowired
    Unleash unleash;

    @Autowired
    MeterRegistry meterRegistry;

    @Qualifier(BUSINESS_LOGIC_SERVICE_V_1)
    @Autowired
    BusinessLogicService businessLogicService;

    @Autowired
    @Qualifier(SECOND_VARIANT_SERVICE_BEAN)
    VariantTestService variantTestService;

    @GetMapping("feature/ifelse-feature/status")
    public void checkIfElseFeatureEnabledForClient(@RequestParam String user,
                                                   @RequestParam String tariff,
                                                   @RequestParam Integer age) {
        UnleashContext context = UnleashContext.builder()
                .userId(user)
                .addProperty("tariff", tariff)
                .addProperty("age", age.toString())
                .build();

        if (unleash.isEnabled("ifelse-feature", context)) {
            Counter.builder("feature.status")
                    .tags("status", "enabled")
                    .tags("feature", "ifelse-feature")
                    .register(meterRegistry)
                    .increment();
        } else {
            Counter.builder("feature.status")
                    .tags("status", "disabled")
                    .tags("feature", "ifelse-feature")
                    .register(meterRegistry)
                    .increment();
        }
    }

    @GetMapping("feature/beans-feature/status")
    public void checkBeansFeatureEnabledForClient() {
        businessLogicService.doSomething();
    }

    @GetMapping("feature/variant-feature/status")
    public void checkVariantFeatureEnabledForClient() {
        variantTestService.doSomething();
    }
}
