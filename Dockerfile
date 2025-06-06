# Usa una imagen de Java
FROM openjdk:17-jdk-slim

# Directorio de la app dentro del contenedor
WORKDIR /app

# Copia el JAR al contenedor
COPY target/programa-lealtad-tconecta-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto del backend
EXPOSE 8080

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
