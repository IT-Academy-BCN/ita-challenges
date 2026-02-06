# ---- build ----
FROM gradle:8.14-jdk21 AS build
WORKDIR /app

COPY gradlew ./
COPY gradle ./gradle
RUN chmod +x ./gradlew

COPY settings.gradle build.gradle ./

COPY challenge-service/build.gradle challenge-service/build.gradle
COPY challenge-service/src challenge-service/src

RUN ./gradlew :challenge-service:bootJar --no-daemon

# ---- runtime ----
FROM eclipse-temurin:21-jre
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*
WORKDIR /app

COPY --from=build /app/challenge-service/build/libs/*-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]