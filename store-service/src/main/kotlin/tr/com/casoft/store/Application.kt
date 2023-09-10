package tr.com.casoft.store

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import tr.com.casoft.store.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
}
