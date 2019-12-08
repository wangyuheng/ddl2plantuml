https://travis-ci.org/wangyuheng/ddl2plantuml.svg?branch=master

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


```plantuml
storage "Context" {
    node ddl
    node db_schema
    node ER
    node template
    node table
    usecase parser
    usecase reader
    usecase writer
}
ddl -down-|> db_schema
db_schema -down-> reader
reader -> table
table -> parser
parser <-up- template
parser -down-> writer
writer -> ER
```

![ddl2plantuml-0](https://image.crick.wang/mweb/15758158152825.jpg)


### 使用

```shell
java -jar ddl2plantuml-1.0.0-SNAPSHOT.jar
```

程序会读取同目录的**ddl.sql**文件，并转换生成**er.puml**文件。

### ER图效果

```plantuml
!define Table(name,desc) class name as "desc" << (T,#FFAAAA) >>
!define primary_key(x) <color:red><b>x</b></color>
!define unique(x) <color:green>x</color>
!define not_null(x) <u>x</u>
hide methods
hide stereotypes
Table(table_1, "table_1\n(This is table 1)"){ 
not_null(id) bigint  column_1 
 not_null(prod_name) varchar  column_2 
 not_null(prod_type) tinyint '0' column_3 0:活期 1:定期 
 not_null(start_time) time  停止交易开始时间 
 not_null(end_time) time  停止交易结束时间 
 not_null(online_type) tinyint '0' 0:上线 1:未上线 
 not_null(prod_info) varchar '' 产品介绍 
 not_null(over_limit) tinyint '0' 超额限制 0:限制 1:不限制 
 not_null(created_time) datetime CURRENT_TIMESTAMP  
 not_null(updated_time) datetime CURRENT_TIMESTAMP  
 } 
  
 Table(table_2, "table_2\n(This is table 2)"){ 
not_null(id) bigint   
 not_null(user_id) bigint  用户id 
 not_null(user_name) varchar  用户名称 
 not_null(prod_id) bigint  产品id 
 interest_date dateNULL计息日期 
 not_null(created_time) datetime CURRENT_TIMESTAMP 创建时间 
 not_null(updated_time) datetime CURRENT_TIMESTAMP 更新时间 
 } 

  
 Table(table_3, "table_3\n(This is table 3)"){ 
not_null(id) bigint   
 not_null(user_id) bigint  用户id 
 not_null(user_name) varchar  用户名称 
 not_null(prod_id) bigint  产品id 
 interest_date dateNULL计息日期 
 not_null(created_time) datetime CURRENT_TIMESTAMP 创建时间 
 not_null(updated_time) datetime CURRENT_TIMESTAMP 更新时间 
 } 

table_1 "1"-->"0..N" table_2
table_1 "1"-->"0..N" table_3
table_2 "1"<-left->"1" table_3

```

![ddl2plantuml-1](http://image.crick.wang/ddl2plantuml-1.jpg)

