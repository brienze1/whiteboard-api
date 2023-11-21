FROM maven:3.9.5-eclipse-temurin-21-alpine

MAINTAINER Luis Brienze <lfbrienze@gmail.com>

WORKDIR /usr/src
COPY . .

ENV SERVER_PORT 8080

RUN mvn clean install -B -Dskiptests -Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=warn

ENTRYPOINT ["java", "-jar", "target/whiteboard-api-0.0.1-SNAPSHOT.jar"]