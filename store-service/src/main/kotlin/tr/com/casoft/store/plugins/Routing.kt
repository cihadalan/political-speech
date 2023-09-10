package tr.com.casoft.store.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File
import java.nio.file.Paths

fun Application.configureRouting() {

    routing {
        route("/store") {
            get("/csv-1") {
                val file = getFileByName("speech1.csv")
                call.respondFile(file)
            }
            get("/csv-2") {
                val file = getFileByName("speech2.csv")
                call.respondFile(file)
            }
        }
    }
}

fun getFileByName(fileName: String): File = Paths.get("files/${fileName}").toAbsolutePath().toFile()
