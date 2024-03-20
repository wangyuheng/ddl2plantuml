package wang.yuheng

import freemarker.cache.ClassTemplateLoader
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.freemarker.*
import io.ktor.server.http.content.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable


fun runWeb(args: Array<String>) {
    val serverConfig = createServerConfig()
    embeddedServer(Netty, host = serverConfig.host, port = serverConfig.port) {
        configureErrorHandle()
        configureSerialization()
        configureTemplating()
        configureRouting()
    }.start(wait = true)


    val host = System.getenv("KTOR_HOST") ?: "0.0.0.0"
    val port = System.getenv("KTOR_PORT")?.toInt() ?: 8080

    embeddedServer(Netty, host = host, port = port) {
        install(ContentNegotiation) {
            json()
        }
        routing {
            post("/d2p") {
                val request = call.receive<Request>()
                StringReader(request.ddl).read().apply { call.respond(ApiWriter(this).message()) }
            }
        }
    }.start(wait = true)
}

private fun Application.configureErrorHandle() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(message = BaseResponse(err = cause.message))
        }
    }
}

private fun Application.configureTemplating() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
}

private fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.configureRouting() {
    routing {
        staticResources("/", "static")
        post("/d2p") {
            val request = call.receive<Request>()
            val response = processRequest(request)
            call.respond(response)
        }
    }
}

private fun processRequest(request: Request): Response {
    val tables = StringReader(request.ddl).read()
    return ApiWriter(tables).message()
}

private fun createServerConfig() = ServerConfig(
    host = System.getenv("KTOR_HOST") ?: "0.0.0.0",
    port = System.getenv("KTOR_PORT")?.toInt() ?: 8080
)

private data class ServerConfig(val host: String, val port: Int)


@Serializable
data class Request(val ddl: String)

@Serializable
open class BaseResponse(open val err: String? = null)

@Serializable
data class Response(val text: String, val previewUrl: String, val previewMarkdown: String) : BaseResponse()


class ApiWriter(private val tables: Iterable<Table>) : Writer {

    override fun write() {
    }

    fun message(): Response {
        val host = System.getenv("PLANTUML_HOST") ?: "https://www.plantuml.com"

        val text = parse(tables)
        val previewUrl = "$host/plantuml/png/~h" + text.toHex()
        val response = Response(text = text, previewUrl = previewUrl, previewMarkdown = "![preview]($previewUrl)")
        return response
    }
}

fun String.toHex(): String {
    return this.toByteArray().joinToString(separator = "") {
        "%02x".format(it)
    }
}


