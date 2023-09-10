package tr.com.casoft.consumer.service

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {

    private val externalServiceHost = "http://localhost:8081"

    private val application = TestApplication {
        externalServices {
            hosts(externalServiceHost) {
                routing {
                    route("/store") {
                        get("/csv-1") {
                            val csvContentByteArray = """
                                Speaker,Topic,Date,Words
                                Alexander Abel, education policty, 2013-10-30, 1000
                                Bernhard Belling, homeland security, 2010-11-05, 1200
                                Caesare Collins, coal subsidies, 2011-11-06, 1100
                                Alexander Abel, coal subsidies, 2012-12-11, 1000
                            """.trimIndent().encodeToByteArray()
                            call.respondBytes(csvContentByteArray)
                        }
                        get("/csv-2") {
                            val csvContentByteArray = """
                                Speaker,Topic,Date,Words
                                Alexander Abel, education policty, 2012-10-30, 500
                                Bernhard Belling, coal subsidies, 2013-11-05, 1800
                                Caesare Collins, coal subsidies, 2011-11-06, 2000
                                Alexander Abel, homeland security, 2012-12-11, 500
                            """.trimIndent().encodeToByteArray()
                            call.respondBytes(csvContentByteArray)
                        }
                    }
                }
            }
        }
    }

    private var service: ConsumerService = ConsumerService(application.client)

    @Test
    fun `evaluate with one valid url should has unique answers`() {
        val pair = listOf(Pair("url", "${externalServiceHost}/store/csv-1"))
        val actual = service.evaluate(pair)
        assertEquals("Alexander Abel", actual.mostSpeeches)
        assertEquals("Bernhard Belling", actual.mostSecurity)
        assertEquals("Caesare Collins", actual.leastWordy)
    }

    @Test
    fun `evaluate with one invalid url should has null answer`() {
        val pair = listOf(Pair("url", "${externalServiceHost}/store/invalid"))
        val actual = service.evaluate(pair)
        assertNull(actual.mostSpeeches)
        assertNull(actual.mostSecurity)
        assertNull(actual.leastWordy)
    }

    @Test
    fun `evaluate with one valid and one invalid urls should have unique answers from valid url`() {
        val pair = listOf(
            Pair("url", "${externalServiceHost}/store/csv-1"),
            Pair("url", "${externalServiceHost}/store/invalid"),
        )
        val actual = service.evaluate(pair)
        assertEquals("Alexander Abel", actual.mostSpeeches)
        assertEquals("Bernhard Belling", actual.mostSecurity)
        assertEquals("Caesare Collins", actual.leastWordy)
    }

    @Test
    fun `evaluate with multi valid urls should has no unique answer`() {
        val pair = listOf(
            Pair("url1", "${externalServiceHost}/store/csv-1"),
            Pair("url2", "${externalServiceHost}/store/csv-2"),
        )
        val actual = service.evaluate(pair)
        assertNull(actual.mostSpeeches)
        assertNull(actual.mostSecurity)
        assertNull(actual.leastWordy)
    }
}
