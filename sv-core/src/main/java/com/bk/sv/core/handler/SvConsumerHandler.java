package com.bk.sv.core.handler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author BK
 * @description: sv 处理器
 * @date 2019/5/9 22:00
 */
public class SvConsumerHandler implements SvHandler {

    private String zookeeperAddress = "localhost:2181";

    private String groupName = "test_group";

    public Object handler(Map<String, Object> params) {
        ExecutorService executor = Executors.newFixedThreadPool(DispatchHandler.consumerCount);
        for (int i = 1; i <= DispatchHandler.consumerCount; i++) {
            executor.submit(new SvConsumer(zookeeperAddress, groupName, "SV_TOPIC_" + i, 10));
        }
        return null;
    }
}
