FROM openjdk:8-jdk-alpine

VOLUME /mnt/data
USER root

ENV DDL=""
ENV PLANTUML=""

COPY target /opt/target
WORKDIR /opt/target

RUN find -type f -name "*-with-dependencies.jar" | xargs -I{} mv {} app.jar

ENTRYPOINT exec java -jar app.jar -o $PLANTUML $DDL