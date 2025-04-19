FROM eclipse-temurin:21-jdk-alpine
WORKDIR /payment
COPY target/payment-0.0.1-SNAPSHOT.jar payment.jar
ENTRYPOINT ["java", "-jar", "payment.jar"]