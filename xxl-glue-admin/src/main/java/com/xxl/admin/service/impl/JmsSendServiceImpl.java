package com.xxl.admin.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.xxl.admin.core.util.JacksonUtil;
import com.xxl.admin.service.IJmsSendService;
import com.xxl.glue.core.broadcast.GlueMessage;

/**
 * JMS.SEND
 * @author xuxueli 2016-1-23 16:40:41
 */
@Service
public class JmsSendServiceImpl implements IJmsSendService {
	private transient static Logger logger = LoggerFactory.getLogger(JmsSendServiceImpl.class);
	
	@Resource
	private JmsTemplate glueTopicPubJmsTemplate;
	
	@Resource
	private JmsTemplate glueQuenuProducerJmsTemplate;
	
	/*
	 * glueTopic pub msg
	 * @see com.xxl.service.IJmsSendService#sendString(java.lang.String)
	 */
	@Override
	public void glueTopicPub(GlueMessage message) {
		String json = JacksonUtil.writeValueAsString(message);
		logger.info("jms simpleTopicPub:{}", json);
		glueTopicPubJmsTemplate.convertAndSend(json);
	}

	/*
	 * glueQuenu produce msg
	 * @see com.xxl.service.IJmsSendService#simpleQuenuProduct(java.lang.String)
	 */
	@Override
	public void glueQuenuProduct(String message) {
		logger.info("jms simpleQuenuProduct:{}", message);
		glueQuenuProducerJmsTemplate.convertAndSend(message);
	}
	
}
