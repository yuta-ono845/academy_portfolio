# 1. ビルド用ステージ
FROM gradle:7.6.1-jdk17 AS builder
WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon

# 2. 実行用ステージ
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=builder /app/build/libs/spring-0.0.1-SNAPSHOT.jar /app.jar
USER root
RUN chmod +x /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
