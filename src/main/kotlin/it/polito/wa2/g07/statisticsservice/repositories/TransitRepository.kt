package it.polito.wa2.g07.statisticsservice.repositories

import it.polito.wa2.g07.statisticsservice.documents.Transit
import it.polito.wa2.g07.statisticsservice.dtos.LongCountDTO
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface TransitRepository : ReactiveMongoRepository<Transit, ObjectId> {


    @Aggregation(pipeline = [
        "{\$addFields: {date: {\$dateToString: {format: '%Y%m%d', date: '\$timestamp'}}}}",
        "{\$match: {date: {\$gte: '?0', \$lte: '?1'}}}",
        "{\$project: {date: 1}}",
        "{\$group: {_id: '\$date', value: {\$count: {}}}}",
    ])
    fun getTransitsCountPerDay(from: String, to: String): Flux<LongCountDTO>

    @Aggregation(pipeline = [
        "{\$addFields: {date: {\$dateToString: {format: '%Y%m%d', date: '\$timestamp'}}}}",
        "{\$match: {date: '?0'}}",
        "{\$addFields: {hour: {\$hour: '\$timestamp'}}}",
        "{\$project: {hour: 1}}",
        "{\$group: {_id: '\$hour', value: {\$count: {}}}}"
    ])
    fun getTransitsCountPerHour(day: String): Flux<LongCountDTO>

    @Aggregation(pipeline = [
        "{\$match: {username: '?2'}}",
        "{\$addFields: {date: {\$dateToString: {format: '%Y%m%d', date: '\$timestamp'}}}}",
        "{\$match: {date: {\$gte: '?0', \$lte: '?1'}}}",
        "{\$addFields: {hour: {\$hour: '\$timestamp'}}}",
        "{\$project: {hour: 1}}",
        "{\$group: {_id: '\$hour', value: {\$count: {}}}}"
    ])
    fun getMyTransitsCountPerHour(from: String, to: String, username: String): Flux<LongCountDTO>
}
