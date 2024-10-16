FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/service-discovery-0.0.1-SNAPSHOT.jar serviceDiscovery.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "serviceDiscovery.jar"]