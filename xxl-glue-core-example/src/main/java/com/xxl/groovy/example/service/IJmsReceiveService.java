package com.xxl.groovy.example.service;

/**
 * JMS.RECEIVE
 * @author xuxueli
 */
public interface IJmsReceiveService {

	/**
	 * glueTopic Sub
	 * @param message
	 */
	public void glueTopicSub(String message);
	
	/**
	 * glueQuenu Consumer
	 * @param message
	 */
	public void glueQuenuConsumer(String message);
	
}