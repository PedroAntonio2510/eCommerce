FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /build
COPY pom.xml ./
COPY src ./src
RUN mvn dependency:go-offline -B && mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk-alpine as runtime
WORKDIR /app

COPY --from=build /build/target/notification-0.0.1-SNAPSHOT.jar ./notification.jar

# Corrigindo o caminho dos arquivos application*.yml
COPY --from=build /build/target/classes/application*.yml /app/config/

ENV TZ='America/Sao_Paulo'

ENTRYPOINT ["java", "-jar", "notification.jar"]
