package com.xxl.admin.service;

import com.xxl.glue.core.broadcast.GlueMessage;

/**
 * JMS.SEND
 * @author xuxueli
 */
public interface IJmsSendService {
	
	/**
	 * glueTopic pub msg
	 * @param message
	 */
	public void glueTopicPub(GlueMessage message);

	/**
	 * glueQuenu produce msg
	 * @param msg
	 */
	public void glueQuenuProduct(String message);
	
}