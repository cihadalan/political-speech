package tr.com.casoft.consumer.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import tr.com.casoft.consumer.service.ConsumerService

fun Application.configureRouting(consumerService: ConsumerService) {
    routing {
        get("/evaluation") {
            val queryParams = call.request.queryParameters.flattenEntries()
            val result = consumerService.evaluate(queryParams)
            call.respond(result)
        }
    }
}
