package tr.com.casoft.consumer.util

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import tr.com.casoft.consumer.constant.HEADER_DATE
import tr.com.casoft.consumer.constant.HEADER_SPEAKER
import tr.com.casoft.consumer.constant.HEADER_TOPIC
import tr.com.casoft.consumer.constant.HEADER_WORDS
import tr.com.casoft.consumer.model.Speech
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader

class CsvUtil {

    companion object {

        private val format: CSVFormat = CSVFormat.DEFAULT.builder()
            .setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true)
            .build()

        fun csvInputStreamToSpeechList(inputStream: InputStream): List<Speech> {
            val reader: Reader = InputStreamReader(inputStream)
            reader.use {
                val csvParser = CSVParser(
                    it,
                    format
                )

                return csvParser.map { record ->
                    Speech(record[HEADER_SPEAKER], record[HEADER_TOPIC], record[HEADER_DATE], record[HEADER_WORDS])
                }
            }
        }

    }
}