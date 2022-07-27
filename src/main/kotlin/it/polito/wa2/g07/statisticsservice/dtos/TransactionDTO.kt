package it.polito.wa2.g07.statisticsservice.dtos

import org.bson.types.ObjectId
import java.util.*

data class TransactionDTO(
    val success: Boolean,
    val ticketAmount: Int,
    val ticketId: ObjectId,
    val cost: Double,
    val date: Date,
    val username: String
)
