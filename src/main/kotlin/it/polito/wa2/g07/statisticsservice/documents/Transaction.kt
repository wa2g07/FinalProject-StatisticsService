package it.polito.wa2.g07.statisticsservice.documents

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document(collection = "transactions")
data class Transaction(
    @Id
    val id: ObjectId = ObjectId.get(),
    val success: Boolean,
    val ticketAmount: Int,
    val ticketId: ObjectId,
    val cost: Double,
    val timestamp: Date,
    val username: String
){}
