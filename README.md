## ddl2plantuml

ddl转换为plantuml格式ER图

### 方案

```plantuml

actor DDL
actor plantUml

cloud {
    control convert #green
}

package {
    node "read ddl" AS step1
    node "extract table info" AS step2
    node "replace by template for plantuml" AS step3
    node "write plantuml" AS step4
}

DDL -right-> convert
convert -right-> plantUml

convert -down-> step1
step1 -right-> step2 
step2 -right-> step3
step3 -right-> step4
step4 -left-> convert

```

![ddl2plantuml-0](http://image.crick.wang/ddl2plantuml-0.jpg)


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

