FROM eclipse-temurin:21-jdk-alpine
WORKDIR /notification
COPY target/notification-0.0.1-SNAPSHOT.jar notification.jar
ENTRYPOINT ["java", "-jar", "notification.jar"]