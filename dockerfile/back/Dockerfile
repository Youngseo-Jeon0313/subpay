FROM openjdk:23-jdk
WORKDIR /subpay/back
CMD ["./gradlew", "clean", "build"]
ARG FILE_NAME=demo-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=build/libs/${FILE_NAME}
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-jar", "/app.jar"]