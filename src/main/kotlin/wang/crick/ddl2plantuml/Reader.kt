package wang.crick.ddl2plantuml

import java.nio.file.Files
import java.nio.file.Paths

/**
 * read a ddl
 *
 * @author wangyuheng@outlook.com
 * @date 2019-12-06 22:00
 */
interface Reader {

    fun read(): List<String>

}

class FileReader(private val path: String) : Reader {

    override fun read(): List<String> {
        return Files.readAllLines(Paths.get(path))
                .filter { !it.startsWith("#") }
                .joinToString("")
                .split(";")
                .map { it.trim() }
                .filter { it.isNotBlank() }
                .toList()
    }

}