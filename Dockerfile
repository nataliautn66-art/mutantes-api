# Usa una imagen base de JDK 21 para la fase de construcción
FROM openjdk:21 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de Gradle y el código fuente necesarios para la compilación
COPY gradlew .
COPY gradle gradle
COPY build.gradle settings.gradle ./
COPY src src

# Ejecuta la construcción de Gradle
RUN chmod +x gradlew
RUN ./gradlew clean build

# --- Segunda Etapa: Ejecución (Usamos la versión JRE más estable) ---
FROM openjdk:21-jre

# Copia el JAR compilado de la etapa 'build'
COPY --from=build /app/build/libs/mutantes-api-0.0.1-SNAPSHOT.jar app.jar

# Define el punto de entrada para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]