# Base image for Java application
FROM openjdk:21-jdk-slim

# Update package list and install Maven
RUN apt-get update && \
    apt-get install -y maven

# Copy application code
COPY . .

# Install Maven dependencies
RUN mvn clean install

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "target/warehousemanagement.jar"]