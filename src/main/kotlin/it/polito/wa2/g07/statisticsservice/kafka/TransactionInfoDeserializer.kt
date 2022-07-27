package it.polito.wa2.g07.statisticsservice.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.kafka.common.errors.SerializationException
import org.apache.kafka.common.serialization.Deserializer
import org.slf4j.LoggerFactory

class TransactionInfoDeserializer : Deserializer<TransactionInfo> {
    private val objectMapper = ObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    override fun deserialize(topic: String?, data: ByteArray?): TransactionInfo? {
        log.info("Deserializing...")
        return objectMapper.readValue(
            String(
                data ?: throw SerializationException("Error when deserializing byte[] to TransactionInfo"),
                Charsets.UTF_8
            ), TransactionInfo::class.java
        )
    }

    override fun close() {}
}