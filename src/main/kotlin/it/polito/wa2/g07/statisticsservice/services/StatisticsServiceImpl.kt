package it.polito.wa2.g07.statisticsservice.services

import it.polito.wa2.g07.statisticsservice.documents.Transaction
import it.polito.wa2.g07.statisticsservice.documents.Transit
import it.polito.wa2.g07.statisticsservice.dtos.DoubleCountDTO
import it.polito.wa2.g07.statisticsservice.dtos.TransactionDTO
import it.polito.wa2.g07.statisticsservice.dtos.LongCountDTO
import it.polito.wa2.g07.statisticsservice.dtos.TransitDTO
import it.polito.wa2.g07.statisticsservice.repositories.TransactionRepository
import it.polito.wa2.g07.statisticsservice.repositories.TransitRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.text.SimpleDateFormat
import java.util.*

@Service
class StatisticsServiceImpl(val transactionRepository: TransactionRepository,
                            val transitRepository: TransitRepository
): StatisticsService {
    override fun saveTransaction(transactionDTO: TransactionDTO) {
        transactionRepository.save(Transaction(
            success = transactionDTO.success,
            ticketAmount = transactionDTO.ticketAmount,
            ticketId = transactionDTO.ticketId,
            cost = transactionDTO.cost,
            timestamp = transactionDTO.date,
            username = transactionDTO.username)
        ).block()
    }

    override fun saveTransit(transitDTO: TransitDTO) {
        transitRepository.save(Transit(
            turnstileId = transitDTO.turnstileId,
            ticketId = transitDTO.ticketId,
            ticketType = transitDTO.ticketType,
            timestamp = transitDTO.timestamp,
            username = transitDTO.username)
        ).block()
    }

    override fun getTransitCountPerDay(from: String, to: String): Flux<LongCountDTO> {
        val today = Date()
        val f = SimpleDateFormat("yyyyMMdd").parse(from)
        val t = SimpleDateFormat("yyyyMMdd").parse(to)
        if(t.before(f))
            throw Exception("To param cannot be in past with respect to from param")
        if(today.before(f))
            throw Exception("From param cannot be in the future with respect to today")
        if(today.before(t))
            throw Exception("To param cannot be in the future with respect to today")
        return transitRepository.getTransitsCountPerDay(from, to)
    }

    override fun getTransitCountPerHour(day: String): Flux<LongCountDTO> {
        val today = Date()
        val d = SimpleDateFormat("yyyyMMdd").parse(day)
        if(today.before(d))
            throw Exception("Day param cannot be in the future with respect to today")
        return transitRepository.getTransitsCountPerHour(day)
    }

    override fun getMyTransitCountPerHour(from: String, to: String, username: String): Flux<LongCountDTO> {
        val today = Date()
        val f = SimpleDateFormat("yyyyMMdd").parse(from)
        val t = SimpleDateFormat("yyyyMMdd").parse(to)
        if(t.before(f))
            throw Exception("To param cannot be in past with respect to from param")
        if(today.before(f))
            throw Exception("From param cannot be in the future with respect to today")
        if(today.before(t))
            throw Exception("To param cannot be in the future with respect to today")
        return transitRepository.getMyTransitsCountPerHour(from, to, username)
    }

    override fun getRevenuesPerMonth(year: Int): Flux<DoubleCountDTO> {
        val today = Date()
        if(today.year + 1900 < year) //date.year return the year - 1900
            throw Exception("Year param cannot be in the future")
        return transactionRepository.getRevenuesPerMonth(year)
    }

    override fun getMyExpensesPerMonth(year: Int, username: String): Flux<DoubleCountDTO> {
        val today = Date()
        if(today.year + 1900 < year) //date.year return the year - 1900
            throw Exception("Year param cannot be in the future")
        return transactionRepository.getMyExpensesPerMonth(year, username)
    }

    override fun getTopBuyers(limit: Int, year: Int): Flux<LongCountDTO> {
        val today = Date()
        if(today.year + 1900 < year) //date.year return the year - 1900
            throw Exception("Year param cannot be in the future")
        return transactionRepository.getTopBuyers(limit, year)
    }

}