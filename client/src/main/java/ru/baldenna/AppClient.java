package ru.baldenna;

import feign.Param;
import feign.RequestLine;

public interface AppClient {

    @RequestLine("GET /feature/ifelse-feature/status?user={user}&tariff={tariff}&age={age}")
    String checkIfElseBasedFeatureEnabled(@Param("user") String user, @Param("tariff") String tariff, @Param("age") Integer age);

    @RequestLine("GET /feature/beans-feature/status?user={user}&tariff={tariff}")
    String checkBeansBasedFeatureEnabled(@Param("user") String user,  @Param("tariff") String customerType);

    @RequestLine("GET feature/variant-feature/status?user={user}&tariff={tariff}")
    String checkVariantBasedFeatureEnabled(@Param("user") String user,  @Param("tariff") String customerType);

}
