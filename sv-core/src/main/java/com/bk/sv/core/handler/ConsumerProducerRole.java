package com.bk.sv.core.handler;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import groovy.util.logging.Slf4j;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author BK
 * @description:
 * @date 2019/5/9 22:45
 */
@Slf4j
public class ConsumerProducerRole {

    private String zookeeperAddress;

    private String groupName;

    private String topicName;

    private int consumerCount;
    private final AtomicInteger COUNT = new AtomicInteger(1);
    //    private static final String ZOOKEEPER = "localhost:2181";
    //    //groupName可以随意给，因为对于kafka里的每条消息，每个group都会完整的处理一遍
    //    private static final String GROUP_NAME = "test_group";
    //    private static final String TOPIC_NAME = "kafka";
    //    private static final int CONSUMER_NUM = 4;
//    private static final int PARTITION_NUM = 4;
    private static final String BROKER_LIST = "localhost:9092";
    private ExecutorService executor;

    ConsumerProducerRole(String zookeeperAddress, String groupName, String topicName, int consumerCount) {
        this.zookeeperAddress = zookeeperAddress;
        this.groupName = groupName;
        this.topicName = topicName;
        this.consumerCount = consumerCount;
        this.executor = Executors.newFixedThreadPool(consumerCount);
    }

    public int handler() {
        ConsumerConnector consumerConnector = initConsumerConnector();
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = consumerConnector.createMessageStreams(ImmutableMap.of(topicName, 1000));
        List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get(topicName);
        if (streams != null && !streams.isEmpty()) {
            List<List<KafkaStream<byte[], byte[]>>> partition = Lists.partition(streams, consumerCount);
            for (final List<KafkaStream<byte[], byte[]>> kafkaStreams : partition) {
                executor.submit(new Runnable() {
                    public void run() {
                        dispatch("SV_TOPIC_" + (COUNT.getAndIncrement() % consumerCount), kafkaStreams);
                    }
                });
            }
        }
        return COUNT.get();
    }


    private Object dispatch(String topicName, Object message) {
        Producer<String, Object> producer = initProducer();
        KeyedMessage<String, Object> productMessage = new KeyedMessage<>(topicName, message);
        producer.send(productMessage);
        return productMessage;
    }

    private Producer<String, Object> initProducer() {
        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", BROKER_LIST);
        ProducerConfig config = new ProducerConfig(props);
        return new Producer<>(config);
    }

    private ConsumerConnector initConsumerConnector() {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeperAddress);
        props.put("zookeeper.connectiontimeout.ms", "1000000");
        props.put("group.id", groupName);
        // Create the connection to the cluster
        ConsumerConfig consumerConfig = new ConsumerConfig(props);
        return Consumer.createJavaConsumerConnector(consumerConfig);
    }

}
