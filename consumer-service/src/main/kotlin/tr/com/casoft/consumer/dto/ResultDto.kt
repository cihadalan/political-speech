package tr.com.casoft.consumer.dto

import kotlinx.serialization.Serializable

@Serializable
data class ResultDto(
    val mostSpeeches: String? = null,
    val mostSecurity: String? = null,
    val leastWordy: String? = null
)

