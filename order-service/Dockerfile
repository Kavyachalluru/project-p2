FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/order-service-0.0.1-SNAPSHOT.jar rev_order.jar

EXPOSE 9090

ENTRYPOINT ["java", "-jar", "rev_order.jar"]