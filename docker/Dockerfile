FROM maven:3.8.4-openjdk-17-slim AS build

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src src

RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim

COPY --from=build /app/target/inditex-pricer-*.jar /inditex-pricer.jar

ENTRYPOINT ["java", "-jar", "/inditex-pricer.jar"]
