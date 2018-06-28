package wang.crick.ddl2plantuml.util;

import com.alibaba.druid.sql.ast.SQLExpr;

/**
 * Expr工具
 *
 * @author wangyuheng
 * @date 2018/6/28 14:12
 */
public class ExprUtil {

    public static String getNullableString(SQLExpr sqlExpr) {
        if (null == sqlExpr) {
            return "";
        } else {
            return sqlExpr.toString();
        }
    }

}
