package wang.yuheng

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable


fun runWeb(args: Array<String>) {
    val serverConfig = createServerConfig()
    embeddedServer(Netty, host = serverConfig.host, port = serverConfig.port) {
        configureSerialization()
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

private fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.configureRouting() {
    routing {
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
data class Response(val data: String)


class ApiWriter(private val tables: Iterable<Table>) : Writer {

    override fun write() {
    }

    fun message(): Response {
        val response = Response(data = parse(tables))
        return response
    }
}

