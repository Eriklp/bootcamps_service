# Usar una imagen base de Java
FROM openjdk:22
WORKDIR /app
COPY . .
RUN ./mvnw clean package
RUN mv target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]