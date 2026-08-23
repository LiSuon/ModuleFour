FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -pl config-service -am -DskipTests

FROM eclipse-temurin:25
WORKDIR /app
COPY --from=builder /build/config-service/target/*.jar app.jar
COPY --from=builder /build/config-service/config_files /config_files
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "app.jar"]
