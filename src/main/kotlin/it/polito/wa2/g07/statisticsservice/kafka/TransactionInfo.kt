package it.polito.wa2.g07.statisticsservice.kafka

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Date

class TransactionInfo (
    @JsonProperty("result")
    val result: String,
    @JsonProperty("orderId")
    val orderId: String,
    @JsonProperty("ticketsAmount")
    val ticketsAmount: Int,
    @JsonProperty("ticketId")
    val ticketId: String,
    @JsonProperty("creditCardNumber")
    val creditCardNumber: String,
    @JsonProperty("expDate")
    val expDate: String,
    @JsonProperty("cvv")
    val cvv: String,
    @JsonProperty("owner")
    val owner: String,
    @JsonProperty("totCost")
    val totCost: Double,
    @JsonProperty("jwt")
    val jwt: String,
    @JsonProperty("date")
    val date: Date
){}