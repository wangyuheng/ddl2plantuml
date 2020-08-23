![Build Status](https://travis-ci.org/wangyuheng/ddl2plantuml.svg?branch=master) [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=alert_status)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml)
 [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=bugs)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml) [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=wangyuheng_ddl2plantuml&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=wangyuheng_ddl2plantuml)

# ddl2plantuml

ddl转换为plantuml格式ER图

## Design

![design diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/design.puml)

程序会读取同目录的**ddl.sql**文件，并转换生成**er.puml**文件。

### ER图效果

![result diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/result.puml)

## Manual

指定sql文件地址，以及输出的plantuml文件地址

### 原生运行文件

根据系统选择不同的native程序

```shell script
./ddl2plantuml_mac ./ddl.sql 
```

### jar

```shell
mvn clean package -Dmaven.test.skip=true 
java -jar target/ddl2plantuml-1.1.0.jar -o ./er_by_jar.puml ./ddl.sql 
```

### docker

[DockerImage](https://hub.docker.com/r/wangyuheng/ddl2plantuml)

打包docker镜像方便使用，需要指定volume用于读取sql文件，以及输出plantuml

```
docker run -e DDL='/mnt/data/ddl.sql' -e PLANTUML='/mnt/data/er_by_docker.puml' -v $(pwd):'/mnt/data' wangyuheng/ddl2plantuml:latest
```

## Develop

### 通过 Graalvm 打包原生应用

1. 安装Graalvm https://www.graalvm.org/docs/getting-started-with-graalvm/

2. 打包jar

```shell script
mvn clean package -Dmaven.test.skip=true 
```

3. 生成native image

```shell script
native-image -jar target/ddl2plantuml-1.1.0.jar ddl2plantuml
```
