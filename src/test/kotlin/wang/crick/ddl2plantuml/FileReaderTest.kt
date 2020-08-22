package wang.crick.ddl2plantuml

import org.junit.jupiter.api.Test
import java.nio.file.Paths

internal class FileReaderTest {

    @Test
    fun read() {
        println(FileReader(Paths.get("./ddl.sql")).read())
    }
}