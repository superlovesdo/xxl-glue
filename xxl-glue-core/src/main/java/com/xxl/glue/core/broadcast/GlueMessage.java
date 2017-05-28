package com.xxl.glue.core.broadcast;

import java.util.Set;

/**
 * Message for Glue broadcast 
 * @author xuxueli 2016-5-20 22:21:06
 */
public class GlueMessage {
	
	/**
	 * glue key (group_name)
	 */
	public String glueKey;
	
	/**
	 * appnames that the glue apply to
	 */
	public Set<String> appNames;
	
	/**
	 * type : 0-sync, 1-delete
	 */
	public GlueMessageType type;

	public String getGlueKey() {
		return glueKey;
	}

	public void setGlueKey(String glueKey) {
		this.glueKey = glueKey;
	}

	public Set<String> getAppNames() {
		return appNames;
	}

	public void setAppNames(Set<String> appNames) {
		this.appNames = appNames;
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
