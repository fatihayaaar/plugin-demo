FROM amazoncorretto:19.0.2-alpine3.17

WORKDIR /app
COPY build/libs/plugindemo-1.0.jar plugindemo.jar

VOLUME /tmp

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "/app/plugindemo.jar"]