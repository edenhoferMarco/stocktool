FROM openjdk:13-jdk-alpine
ARG JAR_FILE=release/*.jar
COPY ${JAR_FILE} app.jar

# Add wait-for-it
COPY wait-for wait-for
RUN chmod +x wait-for

RUN apk update && apk add netcat-openbsd