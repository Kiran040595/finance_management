# Use a lightweight Java runtime
FROM openjdk:11-jdk-slim

# Set working directory
WORKDIR /app

# Copy the Maven wrapper and pom.xml for dependency resolution
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Download dependencies (this will be cached in Docker layers)
RUN ./mvnw dependency:go-offline

# Copy the actual source code
COPY src ./src

# Build the application (creates JAR inside the container)
RUN ./mvnw clean package

# Rename and move JAR file
RUN mv target/*.jar app.jar

# Expose port 8081
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
