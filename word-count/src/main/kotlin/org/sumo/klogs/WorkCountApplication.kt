package org.sumo.klogs

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.TimeWindows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kstream.annotations.KStreamProcessor
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.handler.annotation.SendTo
import java.util.*

@SpringBootApplication
@EnableBinding(KStreamProcessor::class)
class WorkCountApplication

fun main(args: Array<String>) {
    SpringApplication.run(WorkCountApplication::class.java, *args)
}

@Configuration
class WorkCountProcessor(val timeWindows: TimeWindows) {
    private val storeName = "for-WordCounts"

    //@StreamListener("input", condition = "headers['type']=='foo'")
    @StreamListener("input")
    @SendTo("output")
    fun splitStrings(input: KStream<*, String>): KStream<*, WordCount> {

        return input
            .flatMapValues { value -> Arrays.asList(*value.toLowerCase().split("\\W+".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) }
            .map { _, value -> KeyValue(value, value) }
            .groupByKey(Serdes.String(), Serdes.String())
            .count(timeWindows, storeName)
            .toStream()
            .map { key, value -> KeyValue<Any, WordCount>(null, WordCount(key.key(), value, Date(key.window().start()), Date(key.window().end()))) }
    }

}


