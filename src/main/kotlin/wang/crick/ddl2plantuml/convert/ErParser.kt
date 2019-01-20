package wang.crick.ddl2plantuml.convert

import wang.crick.ddl2plantuml.dictionary.Template
import wang.crick.ddl2plantuml.model.Column
import wang.crick.ddl2plantuml.model.Table
import java.util.*
import java.util.stream.Collectors

/**
 * er解析
 *
 * @author wangyuheng
 * @date 2019-01-20 14:10
 */
class ErParser {

    fun parse(tables: List<Table>): String {
        return (Template.ER_HEADER
                + getTableStrs(tables)
                + Template.ER_FOOTER)
    }

    private fun getTableStrs(tables: List<Table>): String {
        return tables.stream()
                .map { table ->
                    (Template.TABLE_LINE.replace(Template.TABLE_NAME.toRegex(), table.name)
                            .replace(Template.TABLE_COMMENT.toRegex(), table.comment)
                            + "{ \n"
                            + getColumnLineStrs(table.columnList)
                            + " \n } \n ")
                }.collect(Collectors.joining(" \n "))
    }

    private fun getColumnLineStrs(columnList: List<Column>): String {
        return columnList.stream().map { column ->
            val line = if (column.notNull) Template.NOT_NULL_COLUMN_LINE else Template.COLUMN_LINE
            line.replace(Template.COLUMN_NAME.toRegex(), column.name)
                    .replace(Template.COLUMN_TYPE.toRegex(), column.type)
                    .replace(Template.COLUMN_DEFAULT.toRegex(), Objects.toString(column.defaultValue, ""))
                    .replace(Template.COLUMN_COMMENT.toRegex(), Objects.toString(column.comment, ""))
        }.collect(Collectors.joining(" \n "))
    }

}