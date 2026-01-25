# ---- build ----
FROM gradle:8.14-jdk21 AS build
WORKDIR /app

COPY settings.gradle build.gradle ./

COPY challenge-service/build.gradle challenge-service/build.gradle
COPY challenge-service/src challenge-service/src

RUN gradle :challenge-service:bootJar --no-daemon

# ---- runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/challenge-service/build/libs/*-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8081
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]