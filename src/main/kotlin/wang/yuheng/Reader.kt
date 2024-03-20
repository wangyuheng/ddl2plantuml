package wang.yuheng

import net.sf.jsqlparser.parser.CCJSqlParserUtil
import net.sf.jsqlparser.statement.create.table.ColumnDefinition
import net.sf.jsqlparser.statement.create.table.CreateTable
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import java.util.stream.Collectors


/**
 * read a ddl file to table
 *
 * @author wangyuheng@outlook.com
 * @date 2019-12-06 22:00
 */
const val DEFAULT_DB_TYPE = "mysql"
const val SEPARATOR = "#wyh_dp#"

interface Reader {

    fun read(dbType: String? = DEFAULT_DB_TYPE): Iterable<Table>

    fun extract(dbType: String, sql: String): Table {
        val statement: CreateTable = CCJSqlParserUtil.parse(sql) as CreateTable
        val name = shaveName(statement.table.name)
        val columnList = extractColumn(statement.columnDefinitions)

        return Table(
            name,
            statement.tableOptionsStrings?.last().toString().shaveComment(),
            columnList
        )
    }

    private fun extractColumn(columnDefinitions: List<ColumnDefinition>): List<Column> {
        return columnDefinitions.stream()
            .map { col ->
                val name = col.columnName.shaveComment()
                var comment = ""
                var defaultValue = ""

                val commentIndex = col.columnSpecs.map { it.toUpperCase() }.indexOf("COMMENT")
                if (commentIndex > -1) {
                    comment = col.columnSpecs[commentIndex + 1]
                }

                val defaultValueIndex = col.columnSpecs.map { it.toUpperCase() }.indexOf("DEFAULT")
                if (defaultValueIndex > -1) {
                    defaultValue = col.columnSpecs[defaultValueIndex + 1]
                }

                //数据类型
                val dataType = col.colDataType
                //类型大小信息
                val dataTypeArguments = dataType.argumentsStringList
                var size = 0
                if (null != dataTypeArguments && dataTypeArguments.size > 0) {
                    size = Integer.parseInt(dataTypeArguments[0].toString())
                }

                val notNull = col.columnSpecs.joinToString(",").toUpperCase().contains("NOT,NULL")
                Column(name, comment, dataType.dataType, size, defaultValue, notNull)
            }.collect(Collectors.toList())
    }

    private fun shaveName(name: String): String {
        return name.replace("`", "").trim()
    }

    private fun Any.shaveComment(): String {
        return Objects.toString(this, "").replace("'", "").trim()
    }

    fun parse(list: List<String>, dbType: String?): Iterable<Table> {
        return list
            .map { it.trimStart() }
            .filter { !it.startsWith("#") }
            .filter { !it.startsWith("--") }
            .joinToString("")
            .replace(Regex("(?s)/\\\\*.*?\\\\*/;"), "")
            .replace(Regex("(?s)/\\\\*.*?\\\\*/"), "")
            .replace("CREATE TABLE ", "$SEPARATOR CREATE TABLE ", ignoreCase = true)
            .split(SEPARATOR)
            .asSequence()
            .map { it.replace(SEPARATOR, "") }
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .filter { it != ";" }
            .map { extract(dbType ?: DEFAULT_DB_TYPE, it) }
            .toList()
    }
}

class FileReader(private val path: Path) : Reader {

    override fun read(dbType: String?): Iterable<Table> {
        return parse(Files.readAllLines(path), dbType)

    }

}

class StringReader(private val ddl: String) : Reader {

    override fun read(dbType: String?): Iterable<Table> {
        return parse(ddl.lines(), dbType)
    }

}

