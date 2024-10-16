FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/api-Gateway-0.0.1-SNAPSHOT.jar gateway.jar

EXPOSE 9089

ENTRYPOINT ["java", "-jar", "gateway.jar"]