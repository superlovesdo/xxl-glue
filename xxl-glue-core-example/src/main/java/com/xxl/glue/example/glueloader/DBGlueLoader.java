package com.xxl.glue.example.glueloader;

import com.xxl.glue.core.loader.GlueLoader;
import org.mybatis.spring.SqlSessionTemplate;

import javax.annotation.Resource;

/**
 * GlueLoader client端实现，如有多个client端集群，推荐将该实现抽象为服务
 * @author xuxueli 2016-1-23 16:59:07
 */
public class DBGlueLoader implements GlueLoader {

	@Resource
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public String load(String name) {
		String source = sqlSessionTemplate.selectOne("GlueInfoMapper.loadSourceByName", name);
		return source;
	}
	
}
