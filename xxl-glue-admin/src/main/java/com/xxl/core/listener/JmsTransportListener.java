package com.xxl.core.listener;

import java.io.IOException;

import org.apache.activemq.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * jms transport listener
 * @author xuxueli 2016-1-23 16:17:00
 */
public class JmsTransportListener implements TransportListener{
	private transient static Logger logger = LoggerFactory.getLogger(JmsTransportListener.class);
	public static int isConnected=1;
	
	@Override
	public void onCommand(Object arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onException(IOException arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void transportInterupted() {
		// TODO Auto-generated method stub
		isConnected=0;
		logger.info(">>>>>>>>>>> jms transport interupted");
	}

	@Override
	public void transportResumed() {
		// TODO Auto-generated method stub
		isConnected=1;
		logger.info(">>>>>>>>>>> jms transport resume");
	}
	
}
