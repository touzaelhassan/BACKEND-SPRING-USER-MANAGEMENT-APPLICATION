FROM openjdk:17-alpine
WORKDIR /app
COPY target/SPRING-USER-MANAGEMENT-APPLICATION-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]