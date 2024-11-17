FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-17-jdk && apt-get clean;

WORKDIR /app

COPY build/libs/app-logger-service-boot.jar /app/

CMD ["java", "-jar", "app-logger-service-boot.jar"]
