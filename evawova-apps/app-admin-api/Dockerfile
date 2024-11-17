FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-17-jdk && apt-get clean;

WORKDIR /app

COPY build/libs/app-admin-api-boot.jar /app/

CMD ["java", "-jar", "app-admin-api-boot.jar"]
