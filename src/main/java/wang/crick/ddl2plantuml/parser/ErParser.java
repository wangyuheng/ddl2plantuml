package wang.crick.ddl2plantuml.parser;

import wang.crick.ddl2plantuml.model.Column;
import wang.crick.ddl2plantuml.model.Table;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * er解析
 *
 * @author wangyuheng
 * @date 2018/6/28 11:13
 */
public class ErParser {

    private static final String ER_HEADER = "@startuml\n" +
            "!define Table(name,desc) class name as \"desc\" << (T,#FFAAAA) >>\n" +
            "!define primary_key(x) <color:red><b>x</b></color>\n" +
            "!define unique(x) <color:green>x</color>\n" +
            "!define not_null(x) <u>x</u>\n" +
            "hide methods\n" +
            "hide stereotypes\n";

    private static final String ER_FOOTER = "\n@enduml";

    private static final String TABLE_NAME = "__table_name__";
    private static final String TABLE_COMMENT = "__table_comment__";
    private static final String COLUMN_NAME = "__column_name__";
    private static final String COLUMN_TYPE = "__column_type__";
    private static final String COLUMN_DEFAULT = "__column_default__";
    private static final String COLUMN_COMMENT = "__column_comment__";

    private static final String TABLE_LINE = "Table(" + TABLE_NAME + ", \"" + TABLE_NAME + "\\n(" + TABLE_COMMENT + ")\")";

    private static final String COLUMN_LINE = COLUMN_NAME + " " + COLUMN_TYPE + "" + COLUMN_DEFAULT + "" + COLUMN_COMMENT;
    private static final String NOT_NULL_COLUMN_LINE = "not_null(" + COLUMN_NAME + ") " + COLUMN_TYPE + " " + COLUMN_DEFAULT + " " + COLUMN_COMMENT;


    public String parse(List<Table> tables) {
        return ER_HEADER
                + getTableStrs(tables)
                + ER_FOOTER;
    }

    private String getTableStrs(List<Table> tables) {
        return tables.stream()
                .map(table -> TABLE_LINE.replaceAll(TABLE_NAME, table.getName())
                        .replaceAll(TABLE_COMMENT, table.getComment())
                        + "{ \n"
                        + getColumnLineStrs(table.getColumnList())
                        + " \n } \n ").collect(Collectors.joining(" \n "));
    }

    private String getColumnLineStrs(List<Column> columnList) {
        return columnList.stream().map(column -> {
            String line = column.isNotNull() ? NOT_NULL_COLUMN_LINE : COLUMN_LINE;
            return line.replaceAll(COLUMN_NAME, column.getName())
                    .replaceAll(COLUMN_TYPE, column.getType())
                    .replaceAll(COLUMN_DEFAULT, Optional.ofNullable(column.getDefaultValue()).orElse(""))
                    .replaceAll(COLUMN_COMMENT, Optional.ofNullable(column.getComment()).orElse(""));
        }).collect(Collectors.joining(" \n "));
    }


}
