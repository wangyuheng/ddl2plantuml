package wang.crick.ddl2plantuml

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class FileReaderTest {

    @Test
    fun read() {
        println(FileReader("./ddl.sql").read())
    }
}