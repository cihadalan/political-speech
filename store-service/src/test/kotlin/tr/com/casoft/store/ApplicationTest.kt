package tr.com.casoft.store

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*
import tr.com.casoft.store.plugins.configureRouting

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/store/csv-1").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
