package wang.crick.ddl2plantuml

import org.junit.Ignore
import org.junit.Test
import java.nio.file.Paths

@Ignore
internal class FileReaderTest {

    @Test
    fun read() {
        println(FileReader(Paths.get("./ddl.sql")).read())
    }
}