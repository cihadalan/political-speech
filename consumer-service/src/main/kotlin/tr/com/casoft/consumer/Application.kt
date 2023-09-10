package tr.com.casoft.consumer

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import tr.com.casoft.consumer.plugins.configureRouting
import tr.com.casoft.consumer.plugins.configureSerialization
import tr.com.casoft.consumer.service.ConsumerService

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val consumerService = ConsumerService(HttpClient(CIO))
    configureSerialization()
    configureRouting(consumerService)
}
