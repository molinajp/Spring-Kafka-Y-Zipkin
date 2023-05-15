#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
WORKDIR /home/app
RUN mvn clean package
#
# Package stage
#
FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/ms-consumer-1.0.0.jar /usr/local/lib/ms-consumer-1.0.0.jar
EXPOSE 8079
ENTRYPOINT ["java","-jar","/usr/local/lib/ms-consumer-1.0.0.jar"]
