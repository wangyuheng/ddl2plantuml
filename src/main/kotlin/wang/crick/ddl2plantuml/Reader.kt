package wang.crick.ddl2plantuml

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition
import com.alibaba.druid.sql.ast.statement.SQLTableElement
import com.alibaba.druid.sql.parser.SQLParserUtils
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import java.util.stream.Collectors

/**
 * read a ddl file to table
 *
 * @author wangyuheng@outlook.com
 * @date 2019-12-06 22:00
 */
interface Reader {

    fun read(dbType: String = "mysql"): List<Table>

    fun extract(dbType: String, sql: String): Table {
        val statement = SQLParserUtils.createSQLStatementParser(sql, dbType).parseCreateTable()

        val name = shaveName(statement.name.simpleName)
        val columnList = extractColumn(statement.tableElementList)

        return Table(
                name,
                statement.comment.shaveComment(),
                columnList
        )
    }

    private fun extractColumn(tableElementList: List<SQLTableElement>): List<Column> {
        return tableElementList.stream()
                .filter { element -> element is SQLColumnDefinition }
                .map { element -> element as SQLColumnDefinition }
                .map { scd ->
                    val name = scd.name.simpleName.replace("`","").trim()
                    val comment = Objects.toString(scd.comment, "").replace("'","").trim()
                    //默认值
                    val defaultValue = scd.defaultExpr?.toString()
                    //数据类型
                    val dataType = scd.dataType
                    //类型大小信息
                    val dataTypeArguments = dataType.arguments
                    var size = 0
                    if (null != dataTypeArguments && dataTypeArguments.size > 0) {
                        size = Integer.parseInt(dataTypeArguments[0].toString())
                    }
                    Column(name, comment, dataType.name, size, defaultValue, scd.containsNotNullConstaint())
                }.collect(Collectors.toList())
    }

    private fun shaveName(name:String):String {
        return name.replace("`","").trim()
    }

    private fun Any.shaveComment():String {
        return Objects.toString(this, "").replace("'","").trim()
    }
}

class FileReader(private val path: String) : Reader {

    override fun read(dbType: String): List<Table> {
        return Files.readAllLines(Paths.get(path))
                .filter { !it.startsWith("#") }
                .joinToString("")
                .split(";")
                .filter { it.isNotBlank() }
                .map { extract(dbType, it) }
                .toList()
    }

}