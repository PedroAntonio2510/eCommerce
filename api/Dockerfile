FROM maven:3.9.9-eclipse-temurin-21-alpine AS build

WORKDIR /app
COPY pom.xml ./
COPY src ./src
RUN mvn dependency:go-offline -B && mvn clean package -DskipTests


FROM eclipse-temurin:21-jdk-alpine as runtime
WORKDIR /app

COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]