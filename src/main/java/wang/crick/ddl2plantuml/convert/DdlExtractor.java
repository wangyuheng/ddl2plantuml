package wang.crick.ddl2plantuml.convert;

import com.alibaba.druid.sql.ast.SQLDataType;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.ast.statement.SQLTableElement;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlPrimaryKey;
import com.alibaba.druid.sql.dialect.mysql.ast.MySqlUnique;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import wang.crick.ddl2plantuml.dictionary.IndexTypeEnum;
import wang.crick.ddl2plantuml.model.Column;
import wang.crick.ddl2plantuml.model.Index;
import wang.crick.ddl2plantuml.util.SqlStringUtil;
import wang.crick.ddl2plantuml.model.Table;
import wang.crick.ddl2plantuml.util.ExprUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * ddl解析
 *
 * @author wangyuheng
 * @date 2018/6/28 11:13
 */
public class DdlExtractor {

    public Table extract(String sql) {
        MySqlStatementParser mySqlStatementParser = new MySqlStatementParser(sql);
        MySqlCreateTableStatement ddl = (MySqlCreateTableStatement) mySqlStatementParser.parseCreateTable();
        List<SQLTableElement> sqlTableElementList = ddl.getTableElementList();
        Table table = new Table();
        table.setName(SqlStringUtil.removeBacktick(ddl.getName().getSimpleName()));
        table.setComment(SqlStringUtil.removeSingleQuote(ExprUtil.getNullableString(ddl.getComment())));

        List<Column> columnList = sqlTableElementList.stream()
                .filter(element -> element instanceof SQLColumnDefinition)
                .map(element -> (SQLColumnDefinition) element)
                .map(scd -> {

                    String name = SqlStringUtil.removeBacktick(scd.getName().getSimpleName()).trim();
                    String comment = SqlStringUtil.removeSingleQuote(ExprUtil.getNullableString(scd.getComment())).trim();
                    //默认值
                    String defaultValue = Optional.ofNullable(scd.getDefaultExpr()).map(Object::toString).orElse(null);
                    //数据类型
                    SQLDataType dataType = scd.getDataType();
                    //类型大小信息
                    List<SQLExpr> dataTypeArguments = dataType.getArguments();
                    Integer size = null;
                    if (null != dataTypeArguments && dataTypeArguments.size() > 0) {
                        size = Integer.parseInt(dataTypeArguments.get(0).toString());
                    }
                    return new Column(name, comment, dataType.getName(), size, defaultValue, scd.containsNotNullConstaint());
                }).collect(Collectors.toList());
        table.setColumnList(columnList);

        Map<String, Column> nameColumnMap = columnList.stream().collect(toMap(ele -> ele.getName().toLowerCase(), Function.identity()));
        List<Index> indexList = sqlTableElementList.stream()
                .filter(element -> element instanceof MySqlKey)
                .map(element -> (MySqlKey) element)
                .map(key -> {
                    Index index;
                    if (key instanceof MySqlPrimaryKey) {
                        index = new Index(IndexTypeEnum.primary, ExprUtil.getNullableString(key.getComment()), getColumnListByKey(key, nameColumnMap));
                    } else if (key instanceof MySqlUnique) {
                        index = new Index(IndexTypeEnum.unique, ExprUtil.getNullableString(key.getComment()), getColumnListByKey(key, nameColumnMap));
                    } else {
                        index = new Index(IndexTypeEnum.ordinary, ExprUtil.getNullableString(key.getComment()), getColumnListByKey(key, nameColumnMap));
                    }
                    return index;
                }).collect(Collectors.toList());
        table.setIndexList(indexList);
        return table;
    }

    private String getLowerColumnName(SQLSelectOrderByItem item) {
        return SqlStringUtil.removeBacktick(item.getExpr().toString()).toLowerCase();
    }

    private List<Column> getColumnListByKey(MySqlKey key, Map<String, Column> nameColumnMap) {
        return key.getColumns().stream()
                .map(this::getLowerColumnName)
                .map(nameColumnMap::get)
                .collect(Collectors.toList());
    }

}
