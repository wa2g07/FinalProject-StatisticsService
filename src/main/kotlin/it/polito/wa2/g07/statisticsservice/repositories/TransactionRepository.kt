package it.polito.wa2.g07.statisticsservice.repositories

import it.polito.wa2.g07.statisticsservice.documents.Transaction
import it.polito.wa2.g07.statisticsservice.dtos.DoubleCountDTO
import it.polito.wa2.g07.statisticsservice.dtos.LongCountDTO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransactionRepository : ReactiveMongoRepository<Transaction, ObjectId> {

    @Aggregation(pipeline = [
        "{\$addFields: {year: {\$year: '\$timestamp'}}}",
        "{\$match: {year: ?0}}",
        "{\$addFields: {month: {\$month: '\$timestamp'}}}",
        "{\$project: {month: 1, cost: 1}}",
        "{\$group: {_id: '\$month', value: {\$sum: '\$cost'}}}"
    ])
    fun getRevenuesPerMonth(year: Int): Flux<DoubleCountDTO>

    @Aggregation(pipeline = [
        "{\$match: {username: '?1'}}",
        "{\$addFields: {year: {\$year: '\$timestamp'}}}",
        "{\$match: {year: ?0}}",
        "{\$addFields: {month: {\$month: '\$timestamp'}}}",
        "{\$project: {month: 1, cost: 1}}",
        "{\$group: {_id: '\$month', value: {\$sum: '\$cost'}}}"
    ])
    fun getMyExpensesPerMonth(year: Int, username: String): Flux<DoubleCountDTO>

    @Aggregation(pipeline = [
        "{\$addFields: {year: {\$year: '\$timestamp'}}}",
        "{\$match: {year: ?1}}",
        "{\$project: {username: 1, ticketAmount: 1}}",
        "{\$group: {_id: '\$username', value: {\$sum: '\$ticketAmount'}}}",
        "{\$sort: {value: -1}}",
        "{\$limit: ?0}"
    ])
    fun getTopBuyers(limit: Int, year: Int): Flux<LongCountDTO>
}