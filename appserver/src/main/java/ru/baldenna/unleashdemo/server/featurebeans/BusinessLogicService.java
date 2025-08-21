package ru.baldenna.unleashdemo.server.featurebeans;

import org.unleash.features.annotation.Toggle;

public interface BusinessLogicService {

    @Toggle(name = "beans-feature", alterBean = "businessLogicServiceV2")
    void doSomething();


}
