FROM openjdk:21-jdk

COPY unleash-agent.jar unleash-agent.jar
COPY UnleashConfig.yaml UnleashConfig.yaml


ENTRYPOINT ["java", "-jar", "unleash-agent.jar", "-f" , "UnleashConfig.yaml" , "-u", "http://unleash:4242" , "-l", "admin",  "-p", "unleash4all"]