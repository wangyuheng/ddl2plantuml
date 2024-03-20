package wang.yuheng.ddl2plantuml

import net.sf.jsqlparser.parser.CCJSqlParserUtil
import org.junit.Ignore
import org.junit.Test
import wang.yuheng.FileReader
import java.nio.file.Paths


internal class FileReaderTest {

    @Test
    fun read() {
        var sql = """
            CREATE TABLE Users (
                id INT PRIMARY KEY,
                username VARCHAR(50) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """.trimIndent()
        var s = CCJSqlParserUtil.parse(sql)
        println(s)
    }
}