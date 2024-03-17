## 1.3.0

- support HTTP Api with ktor
- modify maven to gradle kotlin

## 1.2.0

- support navicat export ddl
- ddl split not dependent ";"

## 1.1.1

- fix docker image `Unable to access jarfile app.jar`

## 1.1.0

- 引入`picocli`封装命令行应用
- 通过`Graalvm`打包Native运行应用
- `jsqlparser`替换`druid`作为sql解析，因为`druid`对`Graalvm`支持有问题
- output 增加Console直接输出，无需指定puml输出文件
