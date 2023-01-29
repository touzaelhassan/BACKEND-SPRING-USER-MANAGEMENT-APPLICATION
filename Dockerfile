FROM openjdk:17-alpine
WORKDIR /app
COPY target/user-management-application.jar app.jar
EXPOSE 8081
CMD ["java", "-jar", "app.jar"]