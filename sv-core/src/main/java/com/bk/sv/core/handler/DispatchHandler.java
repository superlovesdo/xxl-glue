package com.bk.sv.core.handler;

import groovy.util.logging.Slf4j;

import java.util.Map;

/**
 * @author BK
 * @description: sv 处理器
 * @date 2019/5/9 22:00
 */
@Slf4j
public class DispatchHandler implements SvHandler{

    private String zookeeperAddress = "localhost:2181";

    private String groupName ="test_group";

    private String topicName= SvProducer.TOPIC;
    /**
     * 用几个线程来分发到不同的队列中
     */
    public static int consumerCount = 5;


    public Object handler(Map<String, Object> params){
        ConsumerProducerRole consumerProducerRole = new ConsumerProducerRole(zookeeperAddress,groupName,topicName,consumerCount);
        int result = consumerProducerRole.handler();
        System.out.println("一共分发了【"+result+"】个任务！");
        return result;
    }
}
