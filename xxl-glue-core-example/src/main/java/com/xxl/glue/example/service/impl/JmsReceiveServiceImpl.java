package com.xxl.glue.example.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xxl.glue.core.GlueFactory;
import com.xxl.glue.core.broadcast.GlueMessage;
import com.xxl.glue.example.core.util.JacksonUtil;
import com.xxl.glue.example.service.IJmsReceiveService;

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
			GlueMessage glueMessage = JacksonUtil.readValue(message, GlueMessage.class);
			GlueFactory.clearCache(glueMessage);
		}
	}

	@Override
	public void glueQuenuConsumer(String message) {
		logger.info("jms glueQuenuConsumer:{}", message);
	}
	
}
