![Build Status](https://travis-ci.org/wangyuheng/ddl2plantuml.svg?branch=master) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=alert_status)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml)
 [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=bugs)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml)

# ddl2plantuml

parse ddl sql to plantuml ER diagram

Demo -> https://ddl2plantuml.yuheng.wang

| before                                                                                                                                         | after                                                                                                                                                |
|------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------|
| ![sql diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/sql.puml) | ![result diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/result.puml) |

## Run

### Run With Command

Create docker container with file binding.
```shell
docker run --name ddl2plantuml -v $(pwd)/example/ddl.sql:'/ddl.sql' wangyuheng/ddl2plantuml:latest /ddl.sql
```

(Optional) Modify ddl.sql

```shell
vim example/ddl.sql 
```

(Optional) Copy Another file to container

```shell
docker cp anothe-ddl.sql ddl2plantuml:/ddl.sql
```

Start container with attach when container has created

```shell
docker start -a ddl2plantuml
```

### Run With API

```shell
docker run -e D2P_MODE=web -p 8080:8080  wangyuheng/ddl2plantuml:latest
```

Access web with http://localhost:8080 Or request api with HTTP

```shell
curl --location 'localhost:8080/d2p' \
--header 'Content-Type: application/json' \
--data '{
    "ddl": "CREATE TABLE `table_1` (  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '\''column_1'\'',  `prod_name` varchar(20) NOT NULL COMMENT '\''column_2'\'',  `prod_type` tinyint(1) unsigned NOT NULL DEFAULT '\''0'\'' COMMENT '\''column_3 0:活期 1:定期'\'',  `start_time` time NOT NULL COMMENT '\''停止交易开始时间'\'',  `end_time` time NOT NULL COMMENT '\''停止交易结束时间'\'',  `online_type` tinyint(1) unsigned NOT NULL DEFAULT '\''0'\'' COMMENT '\''0:上线 1:未上线'\'',  `prod_info` varchar(2000) NOT NULL DEFAULT '\'''\'' COMMENT '\''产品介绍'\'',  `over_limit` tinyint(1) unsigned NOT NULL DEFAULT '\''0'\'' COMMENT '\''超额限制 0:限制 1:不限制'\'',  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  PRIMARY KEY (`id`)) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='\''This is table 1;'\'';"
}'
```

## Design

![design diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/design.puml)

## Deployment

### Build a fat JAR

```shell
./gradlew :buildFatJar
```

### Build a Docker image

```shell
./gradlew :buildImage
```

```shell
./gradlew :runDocker
```