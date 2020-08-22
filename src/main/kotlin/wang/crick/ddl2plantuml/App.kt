package wang.crick.ddl2plantuml

import picocli.CommandLine
import java.nio.file.Path
import java.util.concurrent.Callable
import kotlin.system.exitProcess

/**
 * parse ddl sql to er plantuml
 *
 * @author wangyuheng@outlook.com
 * @date 2019-01-19 22:00
 */
fun main(args: Array<String>) {

    CommandLine(Convert()).execute(*args)

    val cmd = CommandLine(Convert())
    when {
        args.isEmpty() -> {
            cmd.usage(System.out)
        }
        else -> {
            val exitCode = cmd.execute(*args)
            exitProcess(exitCode)
        }
    }
}

@CommandLine.Command(name = "ddl2plantuml",
        version = ["软件名称：Ddl2plantuml\n版本：V1.0.0"],
        description = ["convert sql ddl to plantuml er"],
        mixinStandardHelpOptions = true
)
class Convert : Callable<Int> {

    @CommandLine.Parameters(index = "0", description = ["The sql ddl file that should be convert to plantuml er."])
    lateinit var src: Path

    @CommandLine.Option(names = ["-o", "--output"], description = ["The dir where the plantuml file to be saved. "])
    private var target: Path? = null

    override fun call(): Int {
        require(src.toFile().exists()) { "ddl file must be existed!" }
        when (target) {
            null -> {
                FileReader(src).read()
                        .apply { ConsoleWriter(this).write() }
            }
            else -> {
                FileReader(src).read()
                        .apply { FileWriter(target!!, this).write() }
            }
        }
        return 0
    }

}
