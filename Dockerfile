# Use the official OpenJDK 21 image as the base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

COPY target/employeeCRUD-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your application listens on (if applicable)
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]
