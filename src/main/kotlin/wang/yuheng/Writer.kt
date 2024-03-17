package wang.yuheng

import java.nio.file.Files
import java.nio.file.Path

interface Writer {

    fun write()

    fun parse(tables: Iterable<Table>): String {
        val template = Thread.currentThread().contextClassLoader.getResource("dot.template")!!.readText()

        val content = tables.joinToString("") { table ->
            val columns = table.columnList.joinToString("\n") { "  ${it.notNullNameWrapper()} ${it.type} ${it.defaultValue} ${it.comment}" }
            "Table(${table.name}, \"${table.name}\\n(${table.comment})\") {\n$columns\n}\n"
        }

        return template.replace("__content__", content)
    }

    private fun Column.notNullNameWrapper(): String {
        return if (this.notNull) {
            "not_null(${this.name})"
        } else {
            this.name
        }
    }
}

class FileWriter(private val path: Path, private val tables: Iterable<Table>) : Writer {

    override fun write() {
        Files.write(path, parse(tables).toByteArray())
    }

}

class ConsoleWriter(private val tables: Iterable<Table>) : Writer {

    override fun write() {
        println(parse(tables))
    }
}