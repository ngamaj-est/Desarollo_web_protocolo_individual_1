# Etapa 1: Compilar el proyecto con Maven
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Etapa 2: Ejecutar en Apache Tomcat
FROM tomcat:10.1-jdk17-temurin
# Eliminar la aplicación por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*
# Copiar el archivo .war generado y renombrarlo a ROOT.war para que abra en la raíz
COPY --from=build /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
CMD ["catalina.sh", "run"]
