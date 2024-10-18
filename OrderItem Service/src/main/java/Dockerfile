# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project jar file into the container
COPY target/revshop-ecommerce.jar /app/revshop-ecommerce.jar

# Make port 9090 available to the world outside this container
EXPOSE 9090

# Run the jar file
ENTRYPOINT ["java", "-jar", "revshop-ecommerce.jar"]
