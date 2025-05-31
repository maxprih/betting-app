FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/balance-service-0.0.1-SNAPSHOT.jar .

EXPOSE 8081

CMD ["java", "-jar", "balance-service-0.0.1-SNAPSHOT.jar"]
