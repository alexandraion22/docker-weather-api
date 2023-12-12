# Build stage
FROM maven:3.8.5-openjdk-17 AS builder

WORKDIR /weather_api
COPY . .
RUN mvn clean install

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /weather_api
COPY --from=builder /weather_api/target/spring-boot-data-mongodb-0.0.1-SNAPSHOT.jar /weather_api/application.jar

CMD ["java", "-jar", "application.jar"]