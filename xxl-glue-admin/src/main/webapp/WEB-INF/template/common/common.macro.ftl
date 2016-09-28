<#macro commonStyle>
  	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/semantic-ui/dist/semantic.min2.css">
  	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/semantic-ui/dist/components/modal.min.css">
  	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/plugins/pace/pace-theme-minimal.css">
  	<style type="text/css">
	body {
		background-color: #FFFFFF;
  	}
  	.ui.menu .item img.logo {
    	margin-right: 1.5em;
  	}
  	.main.container {
    	margin-top: 7em;
  	}
  	.wireframe {
    	margin-top: 2em;
  	}
  	.ui.footer.segment {
		margin: 5em 0em 0em;
    	padding: 5em 0em;
  	}
  	</style>
</#macro>

<#macro commonScript>
<#-- 
1、避免阻塞，提高了 js 的加载性能；
2、通过编程使用 require 方法加载，而不是<script>硬写，更加灵活。
主数据:加载requirejs脚本的script标签加入了data-main属性，这个属性指定的js将在加载完reuqire.js后处理，我们把require.config的配置加入到data-main后，就可以使每一个页面都使用这个配置
data-main还有一个重要的功能，当script标签指定data-main属性时，require会默认的将data-main指定的js为根路径
require参数而为callback函数中发现有$参数，这个就是依赖的jquery模块的输出变量，如果你依赖多个模块，可以依次写入多个参数来使用
通过require加载的模块一般都需要符合AMD规范即使用define来申明模块，但是部分时候需要加载非AMD规范的js，这时候就需要用到另一个功能：shim
shim解释起来也比较难理解，shim直接翻译为"垫"，其实也是有这层意思的，目前我主要用在两个地方
1. 插件形式的非AMD模块，我们经常会用到jquery插件，而且这些插件基本都不符合AMD规范，比如jquery.form插件，这时候就需要将form插件"垫"到jquery中：
-->
<script src="${request.contextPath}/static/plugins/requirejs/requirejs.2.1.22.min.js" data-main="${request.contextPath}/static/js/requirejs.config" ></script>
<script src="${request.contextPath}/static/js/requirejs.config.js" ></script> <!-- 必须手动引用该main入口函数，否则10%几率应为引用地址错误导致部分引用404，费解 -->
<script> var base_url = '${request.contextPath}/'; </script>
</#macro>

<#macro commonHeader>
<div class="ui fixed inverted menu">
	<div class="ui container">
		<a href="${request.contextPath}/code" class="header item"><img class="logo" src="${request.contextPath}/static/image/logo.png">Glue</a>
	    <#--<a href="${request.contextPath}/help" class="item">文档</a>-->
        <a href="https://my.oschina.net/xuxueli/blog/732499" target="_blank" class="item">文档</a>
        <a href="https://github.com/xuxueli/xxl-glue" target="_blank" class="item">Github</a>
	    <#--
	    <a href="${request.contextPath}/code/demoEditor" class="item" target="_blank" >Demo编辑器</a>
	  	<div class="ui simple dropdown item">
			父菜单下拉 <i class="dropdown icon"></i>
			<div class="menu">
				<a class="item" href="#">子菜单A</a>
				<a class="item" href="#">子菜单B</a>
				<div class="divider"></div>
				<div class="header">组标题</div>
				<div class="item">
	            	<i class="dropdown icon"></i>
	            	子菜单组
		            <div class="menu">
		              <a class="item" href="#">子菜单C</a>
		              <a class="item" href="#">子菜单D</a>
		            </div>
				</div>
				<a class="item" href="#">子菜单E</a>
			</div>
		</div>
		-->
		<a href="javascript:;" class="item right logoutBtn">注销</a>
		<#--<a href="#" class="item">注册</a>-->
    </div>
</div>
</#macro>

<#macro commonFooter >
<#--
<div class="ui inverted vertical footer segment">
	<div class="ui container">
		<div class="ui stackable inverted divided equal height stackable grid">
			<div class="three wide column">
				<h4 class="ui inverted header teal">About</h4>
	          	<div class="ui inverted link list">
		            <a href="#" class="item">网站地图</a>
		            <a href="#" class="item">联系我们</a>
		            <a href="#" class="item">意见反馈</a>
		            <a href="#" class="item disabled">赞助</a>
	          	</div>
			</div>
	        <div class="three wide column">
	          	<h4 class="ui inverted header teal">服务</h4>
	          	<div class="ui inverted link list">
		            <a href="#" class="item">在线教程</a>
		            <a href="#" class="item">接入文档</a>
		            <a href="#" class="item">问题汇总</a>
	          	</div>
			</div>
			<div class="seven wide column">
				<h4 class="ui inverted header">热米饭里拌什么最好吃？</h4>
				<p>土豆烧牛肉。赫鲁晓夫说土豆烧牛肉是共产主义，此言不虚。</p>
			</div>
		</div>
	</div>
</div>
-->
</#macro>