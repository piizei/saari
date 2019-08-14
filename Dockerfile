FROM gradle:4.8.1-jdk8-slim AS build
WORKDIR /usr/src/app
USER root
COPY build.gradle .
COPY src ./src
RUN gradle --no-daemon --console plain  bootJar

FROM openjdk:8-jre-slim
COPY --from=build /usr/src/app/build/libs/app-1.0.0.jar /usr/app/app-1.0.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/app/app-1.0.0.jar"]