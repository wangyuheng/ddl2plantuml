FROM openjdk:21-slim

VOLUME /mnt/data
USER root

ENV DDL=""
ENV PLANTUML=""

COPY target /opt/target
WORKDIR /opt/target

RUN find -type f -name "ddl2plantuml.jar" | xargs -I{} mv {} /app.jar

ENTRYPOINT exec java -jar /app.jar -o $PLANTUML $DDL