# Usa una imagen de Java 11
FROM adoptopenjdk:11-jdk-hotspot

WORKDIR /app

# Copia el archivo JAR generado por Maven a la imagen
COPY target/demo-0.0.1-SNAPSHOT.jar /app/demo.jar

# Expone el puerto 8080 en la imagen
EXPOSE 8080

# Comando para ejecutar la aplicación cuando se inicie el contenedor
CMD ["java", "-jar", "/app/demo.jar"]
