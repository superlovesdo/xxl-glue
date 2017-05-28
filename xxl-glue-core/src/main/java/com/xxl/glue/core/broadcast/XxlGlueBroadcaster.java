package com.xxl.glue.core.broadcast;

import com.xxl.glue.core.GlueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * xxl-glue broadcast
 *
 * @author xuxueli 2015-10-29 14:43:46
 */
public class XxlGlueBroadcaster extends XxlZkBroadcastWatcher {
	private static final Logger logger = LoggerFactory.getLogger(XxlGlueBroadcaster.class);

	private static XxlGlueBroadcaster instance = null;
	public static XxlGlueBroadcaster getInstance() {
		return instance;
	}

	public XxlGlueBroadcaster(String zkserver) {
		super(zkserver);
		instance = this;
	}

	/**
	 * broadcast topic : /xxl-glue/glueKey
	 */
	private static final String GLUE_BASE = "/xxl-glue";
	private static final String GLUE_BROADCAST = GLUE_BASE + "/broadcast";

	/**
	 * procuce msg
	 *
	 * @param glueMessage
	 * @return
	 */
	public boolean procuceMsg(GlueMessage glueMessage) {
		String topic = GLUE_BROADCAST + "/" + glueMessage.getName();

		String data = JacksonUtil.writeValueAsString(glueMessage);
		return super.produce(topic, data);
	}

	/**
	 * watch msg
	 *
	 * @param name
	 * @return
	 */
	public boolean watchMsg(String name){
		String topic = GLUE_BROADCAST + "/" + name;

		return watchTopic(topic);
	}

	/**
	 * consume msg
	 *
	 * @param path
	 * @param data
	 */
	@Override
	public void consumeMsg(String path, String data) {
		GlueMessage glueMessage = JacksonUtil.readValue(data, GlueMessage.class);
		GlueFactory.clearCache(glueMessage);
	}

}