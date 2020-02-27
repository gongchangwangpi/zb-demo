package com.kafka.d1;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

import java.util.Properties;

/**
 * @author zhangbo
 * @date 2020-02-27
 */
public class StreamsTest {

    public static void main(String[] args) {

        Topology topology = new Topology();
        topology.addSource("SOURCE", "cluster-partition-3");
        topology.addProcessor("Timestamp", TimestampProcessor::new, "SOURCE");
        topology.addSink("sink", "streams-test", "Timestamp");

        Properties properties = new Properties();
        properties.put("application.id", "kafka-streams-test");
        properties.put("bootstrap.servers", "127.0.0.1:9091,127.0.0.1:9092,127.0.0.1:9093");

        KafkaStreams kafkaStreams = new KafkaStreams(topology, properties);

        kafkaStreams.start();

    }

}
