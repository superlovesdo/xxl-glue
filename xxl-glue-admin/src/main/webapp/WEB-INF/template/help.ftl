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
</head>
<body>
	<@netCommon.commonHeader />
	<div class="ui main text container">
		
		<h1 class="ui dividing header">Glue</h1>

		<h3 class="first">简介</h3>
		<p>推送更行GLUE（抽象且离散的逻辑单元），扩展JVM的动态语言支持；</p>

      	<h3>实现原理</h3>
      	<p>GLUE是一段实现统一接口的Groovy脚本，支持输入输出。通过“Local缓存+广播”的方式进行推送刷新；</p>

      	<h3>特点</h3>
      	<p>1、在线：支持WEB方式在线管理和编辑GLUE；</p>
		<p>2、简单：极简嵌入spring容器；</p>
		<p>3、实时：GLUE变动时主动推送更新并刷新Spring容器中的缓存GLUE；</p>
	    
	    <h3>应用场景</h3>
	    <p>场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项</p>
	    <p>场景B：托管 “静态方法”，可以将配置解析逻辑一并托管，只关注返回结果即可</p>
	    <p>场景B：托管 “抽象且离散的逻辑单元”，可以灵活组装接口和服务（伪服务），作为公共模块</p>
	    
	    <h3>可替代/可补充</h3>
	    <p>分布式缓存：Redis、Memcached（得益于“Local缓存 + 广播”实现方式，可大大减少IO开销）</p>
	    <p>分布式配置：Disconf（得益于Groovy的动态语言特性，可支持复杂类型数据接口，即使代码块）</p>
	    
	    <h3>接入步骤</h3>
	    <p>步骤一：搭建GLUE平台</p>
	    <textarea rows="4" cols="85" style="background-color: rgba(54, 189, 178, 0.21);">
			1、执行底层SQL文件，地址：/db/mysql_xxl_glue.sql；
			2、部署xxl-glue-admin项目；
			3、搭建Activemq服务（Timeout方式更新GLue则可选，广播方式更新Glue则必选）；
	    </textarea>
	    <p>步骤二：引入glue-core依赖（见xxl-glue-core-example中pom.xml）</p>
	    <textarea rows="13" cols="85" style="background-color: rgba(54, 189, 178, 0.21);">
			<!-- GLUE广播更新 -->
			<dependency>
				<groupId>org.apache.activemq</groupId>
				<artifactId>activemq-all</artifactId>
				<version>5.10.2</version>
			</dependency>
			<!-- GLUE -->
			<dependency>
				<groupId>com.xxl</groupId>
				<artifactId>xxl-glue-core</artifactId>
				<version>1.0.2</version>
			</dependency>
	    </textarea>
	    <p>步骤三：配置GlueFactory（见xxl-glue-core-example中applicationcontext-glue.xml）</p>
	    <textarea rows="11" cols="80" style="background-color: rgba(54, 189, 178, 0.21);">
			<bean id="glueFactory" class="com.xxl.glue.core.GlueFactory">
				<property name="cacheTimeout" value="300000" />
				<property name="glueLoader" ref="dbGlueLoader" />
			</bean>
			说明：
				cacheTimeout： GLUE本地缓存时间，如果选择广播更新，该值可适当调大，否则应该调整为较小的值，保证及时更新；
				glueLoader	： GLUE源码加载器，示例项目中选择以DAO作为glueLoader，推荐做法事将GlueLoader封装成公共SOA服务；
	    </textarea>
	    <p>步骤四：执行Glue</p>
	    <textarea rows="3" cols="80" style="background-color: rgba(54, 189, 178, 0.21);">
			Object result = GlueFactory.glue(name, null);
	    </textarea>
	    <p>步骤五：配置Glue广播（非必须）</p>
	    <textarea rows="3" cols="80" style="background-color: rgba(54, 189, 178, 0.21);">
			参考xxl-glue-core-example中applicationcontext-jms-receive.xml
	    </textarea>

	    
  	</div>
	<@netCommon.commonFooter />

<script src="${request.contextPath}/static/plugins/requirejs/requirejs.2.1.22.min.js" data-main="${request.contextPath}/static/js/requirejs.config" ></script>
<script>
var base_url = '${request.contextPath}/';
require(['common'], function(status) {
	console.log(status);
});
</script>
</body>
</html>
