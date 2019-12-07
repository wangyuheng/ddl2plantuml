package wang.crick.ddl2plantuml

/**
 * parse ddl sql to er plantuml
 *
 * @author wangyuheng@outlook.com
 * @date 2019-01-19 22:00
 */
fun main(args: Array<String>) {

    val inPath = args[0]
    val outPath = args[1]

    FileReader(inPath).read()
            .apply { FileWriter(outPath).write(this) }

}

