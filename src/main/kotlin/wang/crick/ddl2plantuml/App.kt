package wang.crick.ddl2plantuml

import com.alibaba.druid.util.StringUtils
import wang.crick.ddl2plantuml.convert.DdlExtractor
import wang.crick.ddl2plantuml.convert.ErParser
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

/**
 * parse ddl sql to er plantuml
 * @author wangyuheng
 * @date 2019-01-19 22:00
 */
fun main(args: Array<String>) {

    val sqlFile = args[0]
    val erFile = args[1]

    val ddlExtractor = DdlExtractor()
    val erParser = ErParser()

    val tables = String(Files.readAllBytes(Paths.get(sqlFile)))
            .split(";")
            .stream()
            .map { sql -> sql.trim() }
            .filter { sql -> !StringUtils.isEmpty(sql) }
            .map { sql -> ddlExtractor.extract(sql) }
            .collect(Collectors.toList())

    val er = erParser.parse(tables)

    Files.write(Paths.get(erFile), er.toByteArray())
}
