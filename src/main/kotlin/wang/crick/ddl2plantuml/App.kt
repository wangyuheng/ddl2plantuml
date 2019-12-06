package wang.crick.ddl2plantuml

import wang.crick.ddl2plantuml.convert.ErParser

/**
 * parse ddl sql to er plantuml
 *
 * @author wangyuheng@outlook.com
 * @date 2019-01-19 22:00
 */
fun main(args: Array<String>) {

    val inPath = args[0]
    val outPath = args[1]

    process(inPath, outPath)

}

fun process(inPath: String, outPath: String) {
    val plantumls = FileReader(inPath).read("mysql")
            .map { ErParser.parse(it) }

    FileWriter(outPath).write(plantumls)
}
