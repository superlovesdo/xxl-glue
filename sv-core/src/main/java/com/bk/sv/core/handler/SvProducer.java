package com.bk.sv.core.handler;

import com.google.common.base.Strings;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class SvProducer {
    public static final String TOPIC = "KAFKA_SV_TOPIC";
    private static final String BROKER_LIST = "localhost:9092";
    private static final String SERIALIZER_CLASS = "kafka.serializer.StringEncoder";
    
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("serializer.class", SERIALIZER_CLASS);
        props.put("metadata.broker.list", BROKER_LIST);
        
        ProducerConfig config = new ProducerConfig(props);
        Producer<String, String> producer = new Producer<String, String>(config);

//        //Send one message.
//        KeyedMessage<String, String> message = new KeyedMessage<String, String>(TOPIC, CONTENT);
//        producer.send(message);
        //Send multiple messages.
//        List<KeyedMessage<String,String>> messages = new ArrayList<KeyedMessage<String, String>>();
        for (int i = 0; i < 100000; i++) {
            KeyedMessage<String, String> message = new KeyedMessage<String, String>(TOPIC, "MESSAGE_" + Strings.padEnd(i+"",6,'0'));
            producer.send(message);
//            messages.add(new KeyedMessage<String, String>(TOPIC, "MESSAGE_" + Strings.padEnd(i+"",6,"0")));
        }
//        producer.send(messages);
    }
}
