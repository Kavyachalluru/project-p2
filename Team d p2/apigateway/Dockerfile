# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory inside the container
WORKDIR /app

COPY target/apigateway-0.0.1-SNAPSHOT.jar apigateway.jar

# Expose the port for the API Gateway
EXPOSE 8082

# Specify the entry point to run the JAR
ENTRYPOINT ["java", "-jar", "apigateway.jar"]
