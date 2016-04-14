package com.xxl.admin.service;

/**
 * JMS.SEND
 * @author xuxueli
 */
public interface IJmsSendService {
	
	/**
	 * glueTopic pub msg
	 * @param message
	 */
	public void glueTopicPub(String message);

	/**
	 * glueQuenu produce msg
	 * @param msg
	 */
	public void glueQuenuProduct(String message);
	
}