package wang.crick.ddl2plantuml

import java.nio.file.Files
import java.nio.file.Paths

interface Writer {

    fun write(tables: Iterable<Table>)

    fun parse(tables: Iterable<Table>): String {
        val template = Thread.currentThread().contextClassLoader.getResource("markdown.template")!!.readText()

        val content = tables.joinToString("") { table ->
            val columns = table.columnList.joinToString("\n") { "${it.notNullNameWrapper()} ${it.type} ${it.defaultValue} ${it.comment}" }
            "Table(${table.name}, \"${table.name}\\n(${table.comment})\"){ \n $columns + \n } \n"
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

class FileWriter(private val path: String) : Writer {

    override fun write(tables: Iterable<Table>) {
        Files.write(Paths.get(path), parse(tables).toByteArray())
    }

}