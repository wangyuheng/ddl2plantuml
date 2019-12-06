package wang.crick.ddl2plantuml.convert

import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition
import com.alibaba.druid.sql.ast.statement.SQLTableElement
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser
import wang.crick.ddl2plantuml.dictionary.IndexTypeEnum
import wang.crick.ddl2plantuml.Column
import wang.crick.ddl2plantuml.Index
import wang.crick.ddl2plantuml.Table
import wang.crick.ddl2plantuml.util.SqlStringUtil
import java.util.*
import java.util.stream.Collectors

/**
 * ddl解析
 *
 * @author wangyuheng
 * @date 2019-01-20 12:53
 */
class DdlExtractor {

    companion object  {
        fun extract(sql: String): Table {
            val mySqlStatementParser = MySqlStatementParser(sql)
            val ddl = mySqlStatementParser.parseCreateTable()
            val sqlTableElementList = ddl.tableElementList

            val columnList = extractColumn(ddl.tableElementList)

            val nameColumnMap = columnList.map { it.name.toLowerCase() to it }.toMap()

            val indexList = sqlTableElementList.stream()
                    .filter { element -> element is MySqlKey }
                    .map { element -> element as MySqlKey }
                    .map { key ->
                        when (key) {
                            is MySqlPrimaryKey -> Index(IndexTypeEnum.PRIMARY, Objects.toString(key.comment, ""), getColumnListByKey(key, nameColumnMap))
                            is MySqlUnique -> Index(IndexTypeEnum.UNIQUE, Objects.toString(key.comment, ""), getColumnListByKey(key, nameColumnMap))
                            else -> Index(IndexTypeEnum.ORDINARY, Objects.toString(key.comment, ""), getColumnListByKey(key, nameColumnMap))
                        }
                    }.collect(Collectors.toList())

            return Table(
                    SqlStringUtil.removeBacktick(ddl.name.simpleName),
                    SqlStringUtil.removeSingleQuote(Objects.toString(ddl.comment, "")),
                    columnList,
                    indexList
            )
        }

        private fun extractColumn(tableElementList: List<SQLTableElement>): List<Column> {
            return tableElementList.stream()
                    .filter { element -> element is SQLColumnDefinition }
                    .map { element -> element as SQLColumnDefinition }
                    .map { scd ->
                        val name = SqlStringUtil.removeBacktick(scd.name.simpleName).trim()
                        val comment = SqlStringUtil.removeSingleQuote(Objects.toString(scd.comment, "")).trim()
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

        private fun getColumnListByKey(key: MySqlKey, nameColumnMap: Map<String, Column>): List<Column?> {
            return key.columns.stream()
                    .map { column -> SqlStringUtil.removeBacktick(column.expr.toString()).toLowerCase() }
                    .map { column -> nameColumnMap[column] }
                    .collect(Collectors.toList())
        }
    }




}