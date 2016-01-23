package com.xxl.groovy.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xxl.groovy.core.GlueFactory;
import com.xxl.groovy.core.cache.LocalCache;
import com.xxl.groovy.example.service.IJmsReceiveService;

/**
 * JMS.RECEIVE
 * @author xuxueli 2016-1-23 16:58:53
 */
@Service("jmsReceiveService")
public class JmsReceiveServiceImpl implements IJmsReceiveService {
	private transient static Logger logger = LoggerFactory.getLogger(JmsReceiveServiceImpl.class);

	@Override
	public void glueTopicSub(String message) {
		logger.info("jms glueTopicSub:{}", message);
		if (message!=null) {
			LocalCache.getInstance().remove(GlueFactory.generateClassCacheKey(message));
			LocalCache.getInstance().remove(GlueFactory.generateInstanceCacheKey(message));
		}
	}

	@Override
	public void glueQuenuConsumer(String message) {
		logger.info("jms glueQuenuConsumer:{}", message);
	}
	
}
