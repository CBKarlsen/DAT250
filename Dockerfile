# Stage 1: Build the application using a Java 21 image and manually install Gradle
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Install Gradle
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-8.3-bin.zip && \
    unzip gradle-8.3-bin.zip && \
    mv gradle-8.3 /opt/gradle && \
    ln -s /opt/gradle/bin/gradle /usr/bin/gradle && \
    rm gradle-8.3-bin.zip

# Copy the project files into the container
COPY --chown=gradle:gradle . .

# Build the application (creates a jar file)
RUN gradle bootJar

# Stage 2: Create the final slim image with JDK 21
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Copy only the jar file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Create a non-root user for security reasons
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expose the port your app runs on (optional, e.g., 8080)
EXPOSE 8080

# Command to run the Spring Boot app
CMD ["java", "-jar", "/app/app.jar"]