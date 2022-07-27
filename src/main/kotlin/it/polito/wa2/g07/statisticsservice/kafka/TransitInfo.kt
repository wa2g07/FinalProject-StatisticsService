package it.polito.wa2.g07.statisticsservice.kafka

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class TransitInfo(
    @JsonProperty("turnstileId")
    val turnstileId: Long,
    @JsonProperty("ticketId")
    val ticketId: Long,
    @JsonProperty("ticketType")
    val ticketType: String,
    @JsonProperty("timestamp")
    val timestamp: Date,
    @JsonProperty("username")
    val username: String
)