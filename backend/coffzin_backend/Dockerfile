# ================================
# STAGE 1: Build
# ================================
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copia o pom.xml e baixa as dependências primeiro (cache do Docker)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copia o restante do código e faz o build
COPY src ./src
RUN mvn clean package -DskipTests

# ================================
# STAGE 2: Run
# ================================
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copia o JAR gerado no stage anterior
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Porta padrão do Spring Boot
EXPOSE 8080

# Inicia a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]