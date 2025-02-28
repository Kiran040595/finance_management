# Use a lightweight Java runtime
FROM openjdk:11-jdk-slim

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Grant execution permission to Maven wrapper
RUN chmod +x mvnw

# Download dependencies (this will be cached)
RUN ./mvnw dependency:go-offline

# Copy the actual source code
COPY src ./src

# Build the application
RUN ./mvnw clean package

# Rename and move JAR file
RUN mv target/*.jar app.jar

# Expose port 8081
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
