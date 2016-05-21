package com.xxl.glue.core.broadcast;

import java.util.Set;

/**
 * Message for Glue broadcast 
 * @author xuxueli 2016-5-20 22:21:06
 */
public class GlueMessage {
	
	/**
	 * glue name
	 */
	public String name;
	
	/**
	 * appnames that the glue apply to
	 */
	public Set<String> appnames;
	
	/**
	 * type : 0-sync, 1-delete
	 */
	public GlueMessageType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getAppnames() {
		return appnames;
	}

	public void setAppnames(Set<String> appnames) {
		this.appnames = appnames;
	}

	public GlueMessageType getType() {
		return type;
	}

	public void setType(GlueMessageType type) {
		this.type = type;
	}

	/**
	 * type for glue message
	 */
	public enum GlueMessageType{
		CLEAR_CACHE, DELETE
	}
	
}
