package ru.baldenna.unleashdemo.server.variantbeans;


import org.unleash.features.annotation.FeatureVariant;
import org.unleash.features.annotation.FeatureVariants;
import org.unleash.features.annotation.Toggle;

import static ru.baldenna.unleashdemo.server.variantbeans.FirstVariantTestService.FIRST_VARIANT_SERVICE_BEAN;
import static ru.baldenna.unleashdemo.server.variantbeans.SecondVariantTestService.SECOND_VARIANT_SERVICE_BEAN;
import static ru.baldenna.unleashdemo.server.variantbeans.ThirdVariantTestService.THIRD_VARIANT_SERVICE_BEAN;

public interface VariantTestService {

    @Toggle(name = "variants-feature",
            variants = @FeatureVariants(
                    fallbackBean = "firstVariantTestService",
                    variants = {
                            @FeatureVariant(name = "firstVariant", variantBean = FIRST_VARIANT_SERVICE_BEAN),
                            @FeatureVariant(name = "secondVariant", variantBean = SECOND_VARIANT_SERVICE_BEAN),
                            @FeatureVariant(name = "thirdVariant", variantBean = THIRD_VARIANT_SERVICE_BEAN),
                    }))
    void doSomething();
}
