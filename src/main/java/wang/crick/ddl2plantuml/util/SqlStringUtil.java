package wang.crick.ddl2plantuml.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * sql字符工具
 *
 * @author wangyuheng
 * @date 2018/6/28 11:31
 */
public class SqlStringUtil {

    public static String removeBacktick(String string) {
        String quote = "`";
        Pattern p = Pattern.compile(quote);
        Matcher m = p.matcher(string);
        return m.replaceAll("");
    }

    public static String removeSingleQuote(String string) {
        String singleQuote = "'";
        Pattern p = Pattern.compile(singleQuote);
        Matcher m = p.matcher(string);
        return m.replaceAll("");
    }
}
