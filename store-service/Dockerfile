FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean buildFatJar --no-daemon

FROM openjdk:11
EXPOSE 8081
RUN mkdir /app
WORKDIR /app
COPY files files
COPY --from=build /home/gradle/src/build/libs/*-all.jar store-service.jar
ENTRYPOINT ["java","-jar","store-service.jar"]