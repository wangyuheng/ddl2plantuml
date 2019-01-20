package wang.crick.ddl2plantuml.dictionary

/**
 * 模板
 * @author wangyuheng
 * @date 2019-01-20 14:16
 */
class Template {

    companion object {
        const val ER_HEADER = "@startuml\n" +
                "!define Table(name,desc) class name as \"desc\" << (T,#FFAAAA) >>\n" +
                "!define primary_key(x) <color:red><b>x</b></color>\n" +
                "!define unique(x) <color:green>x</color>\n" +
                "!define not_null(x) <u>x</u>\n" +
                "hide methods\n" +
                "hide stereotypes\n"

        const val ER_FOOTER = "\n@enduml"

        const val TABLE_NAME = "__table_name__"
        const val TABLE_COMMENT = "__table_comment__"
        const val COLUMN_NAME = "__column_name__"
        const val COLUMN_TYPE = "__column_type__"
        const val COLUMN_DEFAULT = "__column_default__"
        const val COLUMN_COMMENT = "__column_comment__"

        const val TABLE_LINE = "Table($TABLE_NAME, \"$TABLE_NAME\\n($TABLE_COMMENT)\")"

        const val COLUMN_LINE = "$COLUMN_NAME $COLUMN_TYPE$COLUMN_DEFAULT$COLUMN_COMMENT"
        const val NOT_NULL_COLUMN_LINE = "not_null($COLUMN_NAME) $COLUMN_TYPE $COLUMN_DEFAULT $COLUMN_COMMENT"
    }


}