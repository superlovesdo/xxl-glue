<#import "/common/common.macro.ftl" as netCommon>
<!DOCTYPE html>
<html>
<head>
  	<!-- Standard Meta -->
  	<meta charset="utf-8" />
  	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  	<!-- Site Properities -->
  	<title>Glue</title>
	<@netCommon.commonStyle />
	<!-- input -->
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/semantic-ui/dist/components/input.css">
	<!-- datatables -->
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/datatables/media/css/jquery.dataTables.min.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/datatables/media/css/jquery.dataTables_themeroller.css">
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/datatables/media/css/dataTables.bootstrap.min.css">
</head>
<body>
	<@netCommon.commonHeader />
	
	<!-- code搜索框 -->
	<div class="ui column stackable grid" style="padding: 1em;margin-top: 50px;">
	  	<div class="column">
			<div class="ui action left icon input">
		      	<i class="search icon"></i>
		      	<input type="text" placeholder="请输入codeName..." id="codeName" >
		      	<div class="ui default button" id="searchBtn" >Search</div>
		    </div>
	      	<button class="ui teal button" id="addBtn">新增</button>
	    </div>
    </div>
    
	<!-- code列表 -->
	<div class="column" style="padding: 1em;margin-top: 5px;">
		<table class="ui right aligned table" id="code_list" >
			<thead>
		        <th class="left aligned">id</th>
		        <th>Glue名称</th>
		        <th>源码</th>
		        <th>简介</th>
		        <th>新增时间</th>
		        <th>更新时间</th>
		        <th>操作</th>
			</thead>
	      	<tbody></tbody>
		</table>
	</div>
	
	<@netCommon.commonFooter />
	
	<!-- code：新增 -->
	<div class="ui modal" id="addModal" >
	  	<i class="close icon"></i>
	  	<div class="header">新增</div>
	  	<div class="content">
	    	<form class="ui large form">
		      	<div class="ui stacked segment2">
					<div class="field">
						<div class="ui labeled input">
					      <a class="ui label">名称</a>
					      <input type="text" name="name" placeholder="请输入...">
					    </div>
					</div>
					<div class="field">
						<div class="ui labeled input">
					      <a class="ui label">简介</a>
					      <input type="text" name="about" placeholder="请输入...">
					    </div>
					</div>
					<div class="actions">
						<div class="ui button labeled icon green submit">保存<i class="checkmark icon"></i></div>
						<div class="ui button negative">取消</div>
				    </div>
		      	</div>
		      	<div class="ui error message"></div>
		      	<input type="text" name="remark" value="Demo代码初始化" style="display:none;" >
<textarea name="source" style="display:none;" >
package com.xxl.groovy.example.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * Demo GlueHandler
 * 
 * @author xuxueli 2016-4-14 15:36:37
 */
public class DemoGlueHandler implements GlueHandler {
	private static Logger Logger = LoggerFactory.getLogger(DemoGlueHandler.class);

	@Override
	public Object handle(Map<String, Object> params) {
		Logger.warn("GLUE : Hello World .");
		return new Object();
	}

}
</textarea>
		    </form>
	  	</div>
	</div>
	
	<!-- code：同步 -->
	<div class="ui modal" id="clearCacheModal" >
	  	<i class="close icon"></i>
	  	<div class="header">清除GLUE缓存</div>
	  	<div class="content">
	    	<form class="ui large form">
		      	<div class="ui stacked segment2">
					<div class="field">
						<div class="ui labeled input">
					      <a class="ui label">Witch App：</a>
					      <input type="text" name="appNames" placeholder="请输入待清除GLUE缓存的AppName，如多个则用逗号分隔，为空则全站清除">
					    </div>
					</div>
					<input type="hidden" name="id" >
					<div class="actions">
						<div class="ui button labeled icon green submit">确定<i class="checkmark icon"></i></div>
						<div class="ui button negative">取消</div>
				    </div>
		      	</div>
		      	<div class="ui error message"></div>
		    </form>
	  	</div>
	</div>

<@netCommon.commonScript />
<script>
require(['common']);
require(['code.index']);
</script>
</body>
</html>
