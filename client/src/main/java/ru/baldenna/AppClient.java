package ru.baldenna;

import feign.Param;
import feign.RequestLine;

public interface AppClient {

    @RequestLine("GET /feature/ifelse-feature/status?user={user}&customerType={customerType}&age={age}")
    String checkIfElseBasedFeatureEnabled(@Param("user") String user, @Param("customerType") String customerType, @Param("age") Integer age);

    @RequestLine("GET /feature/beans-feature/status?user={user}&customerType={customerType}")
    String checkBeansBasedFeatureEnabled(@Param("user") String user,  @Param("customerType") String customerType);

    @RequestLine("GET feature/variant-feature/status?user={user}&customerType={customerType}")
    String checkVariantBasedFeatureEnabled(@Param("user") String user,  @Param("customerType") String customerType);

}
