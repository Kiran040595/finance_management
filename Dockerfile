# Use OpenJDK 11
FROM openjdk:11-jdk-slim

# Set working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/finance-management-1.0.0.jar app.jar

# Expose port 8081 (Spring Boot application)
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
