package wang.crick.ddl2plantuml

import java.nio.file.Files
import java.nio.file.Paths

interface Writer {

    fun write(plantumls: List<String>)
}

class FileWriter(private val path: String) : Writer {

    override fun write(plantumls: List<String>) {
        Files.write(Paths.get(path), plantumls)
    }

}