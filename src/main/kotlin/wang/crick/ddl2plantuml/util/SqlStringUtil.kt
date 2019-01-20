package wang.crick.ddl2plantuml.util

import java.util.regex.Pattern

/**
 * sql字符工具
 *
 * @author wangyuheng
 * @date 2019-01-20 13:17
 */
object SqlStringUtil {

    private val backtick = Pattern.compile("`")
    private val singleQuote = Pattern.compile("'")

    fun removeBacktick(string: String): String {
        return backtick.matcher(string).replaceAll("")
    }

    fun removeSingleQuote(string: String): String {
        return singleQuote.matcher(string).replaceAll("")
    }
}