# ---- build ----
FROM gradle:8.14-jdk21 AS build
WORKDIR /app

COPY gradlew ./gradlew
COPY gradle ./gradle
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew

COPY settings.gradle build.gradle ./

COPY account-service/build.gradle account-service/build.gradle
COPY account-service/src account-service/src

RUN ./gradlew :account-service:bootJar --no-daemon

# ---- runtime ----
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app

COPY --from=build /app/account-service/build/libs/*.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
