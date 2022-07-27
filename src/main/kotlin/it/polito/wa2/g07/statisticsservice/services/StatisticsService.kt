package it.polito.wa2.g07.statisticsservice.services

import it.polito.wa2.g07.statisticsservice.dtos.DoubleCountDTO
import it.polito.wa2.g07.statisticsservice.dtos.TransactionDTO
import it.polito.wa2.g07.statisticsservice.dtos.LongCountDTO
import it.polito.wa2.g07.statisticsservice.dtos.TransitDTO
import reactor.core.publisher.Flux

interface StatisticsService {
    fun saveTransaction(transactionDTO: TransactionDTO)
    fun saveTransit(transitDTO: TransitDTO)
    fun getTransitCountPerDay(from: String, to: String): Flux<LongCountDTO>
    fun getTransitCountPerHour(day: String): Flux<LongCountDTO>
    fun getMyTransitCountPerHour(from: String, to: String, username: String): Flux<LongCountDTO>
    fun getRevenuesPerMonth(year: Int): Flux<DoubleCountDTO>
    fun getMyExpensesPerMonth(year: Int, username: String): Flux<DoubleCountDTO>
    fun getTopBuyers(limit: Int, year: Int): Flux<LongCountDTO>
}