# ---- build ----
FROM gradle:8.14-jdk21 AS build
WORKDIR /app

COPY settings.gradle build.gradle ./

COPY user-service/build.gradle user-service/build.gradle
COPY user-service/src user-service/src

RUN gradle :user-service:bootJar --no-daemon

# ---- runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/user-service/build/libs/*-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8082
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]