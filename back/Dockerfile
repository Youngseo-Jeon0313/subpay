FROM gradle:8.5-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon -x test
RUN ls -l /home/gradle/src/build/libs

FROM openjdk:17-jdk-slim
ENV TZ=Asia/Seoul
COPY --from=build /home/gradle/src/build/libs/*SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
