package wang.yuheng

import picocli.CommandLine
import java.nio.file.Path
import java.util.concurrent.Callable
import kotlin.system.exitProcess

fun runCmd(args: Array<String>) {
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

@CommandLine.Command(
    name = "ddl2plantuml",
    version = ["软件名称：Ddl2plantuml", "版本：V1.1.0"],
    description = ["convert sql @|bold,fg(red) ddl|@ to @|bold,fg(red) plantuml|@ er"],
    mixinStandardHelpOptions = true
)
class Convert : Callable<Int> {

    @CommandLine.Parameters(
        index = "0",
        paramLabel = "FILE",
        description = ["The sql ddl file that should be convert to plantuml er."]
    )
    lateinit var src: Path

    @CommandLine.Option(
        names = ["-o", "--output"],
        description = ["The file where the plantuml file to be saved. default is console "]
    )
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
