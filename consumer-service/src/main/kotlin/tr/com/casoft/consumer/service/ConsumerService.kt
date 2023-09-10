package tr.com.casoft.consumer.service

import io.ktor.client.*
import io.ktor.client.call.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import tr.com.casoft.consumer.dto.ResultDto
import io.ktor.client.request.*
import io.ktor.http.*
import tr.com.casoft.consumer.model.Speech
import tr.com.casoft.consumer.util.CsvUtil
import java.io.InputStream


class ConsumerService(private val client: HttpClient) {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)


    fun evaluate(allRequestParams: List<Pair<String, String>>): ResultDto {
        logger.info("Getting query params: {}", allRequestParams)
        val list = runBlocking {
            val deferredList = allRequestParams.map { queryParam ->
                async(Dispatchers.IO) {
                    try {
                        val response = client.get(queryParam.second)
                        if (response.status == HttpStatusCode.OK) {
                            val body: InputStream = response.body()
                            CsvUtil.csvInputStreamToSpeechList(body)
                        } else {
                            logger.error(
                                "Error occurred during getting data from the store service. Error status: {}",
                                response.status
                            )
                            null
                        }
                    } catch (ex: Exception) {
                        logger.error("Error occurred during getting data from the store service: {}", ex.message)
                        null
                    }
                }
            }

            deferredList.awaitAll().filterNotNull().flatten()
        }

        return if (list.isEmpty()) {
            ResultDto()
        } else {
            val mostSpeeches = getMostSpeechesByYear(list, "2013")
            val mostSecurity = getMostSpeechesByTopic(list, "homeland security")
            val leastWordy = getLeastWordy(list)
            return ResultDto(mostSpeeches, mostSecurity, leastWordy)
        }
    }


    private fun getMostSpeechesByYear(list: List<Speech>, year: String): String? {
        val groupBySpeaker = list.filter { it.date.contains(year) }.groupBy { it.speaker }
        val maxYearSpeech = groupBySpeaker.maxByOrNull { it.value.size }?.toPair()
        return getUniqueOrNullAnswer(maxYearSpeech, groupBySpeaker)
    }


    private fun getMostSpeechesByTopic(list: List<Speech>, topic: String): String? {
        val groupBySpeaker = list.filter { it.topic == topic }.groupBy { it.speaker }
        val maxTopicSpeech = groupBySpeaker.maxByOrNull { it.value.size }?.toPair()
        return getUniqueOrNullAnswer(maxTopicSpeech, groupBySpeaker)
    }

    private fun getLeastWordy(list: List<Speech>): String? {
        val groupBySpeaker = list.filter { it.words.toIntOrNull() != null }.groupBy { it.speaker }
        val minWordSpeech = groupBySpeaker.minByOrNull { it.value.sumOf { v -> v.words.toInt() } } ?: return null
        val minWordCount = minWordSpeech.value.sumOf { it.words.toInt() }
        groupBySpeaker.values.singleOrNull { it.sumOf { v -> v.words.toInt() } == minWordCount } ?: return null
        return minWordSpeech.key
    }

    private fun getUniqueOrNullAnswer(
        speech: Pair<String, List<Speech>>?,
        groupBySpeaker: Map<String, List<Speech>>
    ): String? {
        if (speech != null && groupBySpeaker.isNotEmpty()) {
            val speechCount = speech.second.size
            groupBySpeaker.values.singleOrNull { it.size == speechCount } ?: return null
            return speech.first
        }
        return null
    }

}