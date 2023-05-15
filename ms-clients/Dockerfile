#
# Build stage
#
FROM maven:3.8.5-openjdk-17-slim AS build
ARG src="Spring Common Logger"
COPY ${src} /build-logger
COPY ms-clients /build-app
RUN mvn -f ./build-logger/pom.xml clean install
RUN mvn -f ./build-app/pom.xml clean package
#
# Extractor stage
#
FROM openjdk:17-jdk-alpine AS extractor
ARG APPLICATION_DIR=/application
WORKDIR ${APPLICATION_DIR}
COPY --from=build /build-app/target/*.jar .
RUN java -Djarmode=layertools -jar *.jar extract
#
# Package stage
#
FROM openjdk:17-jdk-alpine AS final
ARG SOURCE_BASE_DIR=application
RUN addgroup grpapp
RUN adduser -S appservice -G grpapp
WORKDIR /gateway
COPY --from=extractor ${SOURCE_BASE_DIR}/dependencies/ ./
COPY --from=extractor ${SOURCE_BASE_DIR}/spring-boot-loader/ ./
COPY --from=extractor ${SOURCE_BASE_DIR}/snapshot-dependencies/ ./
COPY --from=extractor ${SOURCE_BASE_DIR}/application/ ./
USER appservice
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

