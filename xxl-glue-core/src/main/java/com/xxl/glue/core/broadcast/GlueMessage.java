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
	 * type : 0-update, 1-delete, 2-add
	 */
	public int type;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
