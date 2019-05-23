package com.bk.sv.core.handler;

import com.google.common.collect.ImmutableMap;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SvConsumer implements Runnable{
    private String zookeeperAddress;

    private String groupName;

    private String topicName;

    private int workTheadCount;
    //    private static final String ZOOKEEPER = "localhost:2181";
    //    //groupName可以随意给，因为对于kafka里的每条消息，每个group都会完整的处理一遍
    //    private static final String GROUP_NAME = "test_group";
    //    private static final String TOPIC_NAME = "kafka";
    //    private static final int CONSUMER_NUM = 4;
//    private static final int PARTITION_NUM = 4;

    private ExecutorService executor;

    SvConsumer(String zookeeperAddress, String groupName, String topicName, int workTheadCount) {
        this.zookeeperAddress = zookeeperAddress;
        this.groupName = groupName;
        this.topicName = topicName;
        this.workTheadCount = workTheadCount;
        this.executor = Executors.newFixedThreadPool(workTheadCount);
    }

    public void consume() {
        Map<String, List<KafkaStream<byte[], byte[]>>> topicMessageStreams = initConsumerConnector().createMessageStreams(ImmutableMap.of(topicName, workTheadCount));
        List<KafkaStream<byte[], byte[]>> streams = topicMessageStreams.get(topicName);
        while (streams!=null){
            for (final KafkaStream<byte[], byte[]> stream : streams) {
                executor.submit(new Runnable() {
                    public void run() {
                        for (MessageAndMetadata<byte[], byte[]> msgAndMetadata : stream) {
                            System.out.println("consume: " + new String(msgAndMetadata.message()));
                        }
                    }
                });
            }
            streams = topicMessageStreams.get(topicName);
        }
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

    @Override
    public void run() {
        consume();
    }
}
