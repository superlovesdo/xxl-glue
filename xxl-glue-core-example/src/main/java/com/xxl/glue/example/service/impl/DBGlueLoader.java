package com.xxl.glue.example.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xxl.glue.core.GlueLoader;
import com.xxl.glue.example.core.model.CodeInfo;
import com.xxl.glue.example.dao.ICodeInfoDao;

/**
 * GlueLoader client端实现，如有多个client端集群，推荐将该实现抽象为服务
 * @author xuxueli 2016-1-23 16:59:07
 */
@Repository("dbGlueLoader")
public class DBGlueLoader implements GlueLoader {

	@Resource
	private ICodeInfoDao codeInfoDao;
	
	@Override
	public String load(String name) {
		CodeInfo codeInfo = codeInfoDao.loadCodeByName(name);
		if (codeInfo!=null) {
			return codeInfo.getSource();
		}
		return null;
	}
	
}
