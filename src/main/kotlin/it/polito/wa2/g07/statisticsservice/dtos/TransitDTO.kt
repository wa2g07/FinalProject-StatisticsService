package it.polito.wa2.g07.statisticsservice.dtos

import org.bson.types.ObjectId
import java.util.*

data class TransitDTO(
    val turnstileId: Long,
    val ticketId: Long,
    val ticketType: ObjectId,
    val timestamp: Date,
    val username: String
)
