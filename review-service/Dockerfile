FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/review-service-0.0.1-SNAPSHOT.jar reviewService.jar
EXPOSE 9091
ENTRYPOINT ["java", "-jar", "reviewService.jar"]
