![Build Status](https://travis-ci.org/wangyuheng/ddl2plantuml.svg?branch=master)

## ddl2plantuml

ddl转换为plantuml格式ER图

### 运行方式

指定sql文件地址，以及输出的plantuml文件地址

#### jar

```shell
java -jar ddl2plantuml.jar ./ddl.sql ./er_by_jar.puml
```

#### docker

打包docker镜像方便使用，需要指定volume用于读取sql文件，以及输出plantuml

```
docker run -e DDL='/mnt/data/ddl.sql' -e PLANTUML='/mnt/data/er_by_docker.puml' -v $(pwd):'/mnt/data' wangyuheng/ddl2plantuml:latest
```

### 方案

![design diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/design.puml)

### 使用

```shell
java -jar ddl2plantuml-1.0.0-SNAPSHOT.jar
```

程序会读取同目录的**ddl.sql**文件，并转换生成**er.puml**文件。

### ER图效果

![result diagram](http://www.plantuml.com/plantuml/proxy?src=https://raw.githubusercontent.com/wangyuheng/ddl2plantuml/master/.plantuml/result.puml)
