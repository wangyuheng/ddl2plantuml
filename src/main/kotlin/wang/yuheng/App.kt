package wang.yuheng

fun main(args: Array<String>) {

    val mode = System.getenv("D2P_MODE")?.lowercase() ?: "cmd"
    when (mode) {
        "web" -> runWeb(args)
        "cmd" -> runCmd(args)
        else -> runWeb(args)
    }

}
