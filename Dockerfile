FROM eclipse-temurin:11-jre
WORKDIR /app
COPY target/codigo-barras-api-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]