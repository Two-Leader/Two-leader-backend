#FROM openjdk:17 AS builder
#
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY src src
#
#RUN chmod +x ./gradlew
#RUN ./gradlew bootJAR
#
#
#FROM openjdk:17
#
## 위 builder에서 만든 .jar파일을 컨테이너 내부로 복사
#COPY --from=builder build/libs/*.jar app.jar
#EXPOSE 8080
#
## 이미지 빌드시 실행되는 명령어. 로컬에서 jar파일을 구동하는 것과 동일한 명령어
#ENTRYPOINT ["java", "-jar", "/app.jar"]


FROM gradle:7.6.1-jdk17 as cache
RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
COPY build.gradle /home/gradle/java-code/
WORKDIR /home/gradle/java-code
RUN gradle clean build -i --stacktrace

FROM gradle:7.6.1-jdk17 as builder
COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle
COPY . /usr/src/java-code/
WORKDIR /usr/src/java-code
RUN gradle bootJar -i --stacktrace

FROM openjdk:17.0.2-jdk-slim
EXPOSE 8080
USER root
WORKDIR /usr/src/java-app
COPY --from=builder /usr/src/java-code/build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]