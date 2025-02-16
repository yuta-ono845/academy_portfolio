FROM eclipse-temurin:17

WORKDIR /app

# JAR ファイルをコンテナにコピー（`build/libs/` の JAR を /app に移動）
COPY build/libs/spring-0.0.1-SNAPSHOT.jar app.jar

# アプリの実行コマンド
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
