# Use OpenJDK as base image
FROM eclipse-temurin:21-jdk as DEVELOPMENT
# Set working directory inside the container
WORKDIR /app

# Copy only dependencies first for caching (improves build performance)
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

# Copy the application source code
COPY src ./src

# Expose application port
EXPOSE 8080

# Start the application with Devtools enabled
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev", \
     "-Dspring.devtools.restart.enabled=true", \
     "-Dspring.devtools.livereload.enabled=true", \
     "-Dspring.devtools.remote.secret=mysecret"]