#! /bin/bash

docker run -e DDL='/mnt/data/ddl.sql' -e PLANTUML='/mnt/data/er_by_docker.puml' -v $(pwd):'/mnt/data' wangyuheng/ddl2plantuml:latest