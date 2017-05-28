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
    <style>
    html {height: 100%;width: 100%;overflow: hidden;}
	body {overflow: hidden;margin: 0;padding: 0;height: 100%;width: 100%;font-size: 12px;font-family: Arial, Helvetica, sans-serif, Tahoma, Verdana, sans-serif;background:black;color: black;}
	#ace-editor {position: absolute;top: 50px;left: 10px;bottom: 10px;right: 0px;/*background: white;*/}
    </style>
</head>
<body>
<!-- tools -->
<div class="ui fixed inverted menu">
	<div class="ui container">
	    <a href="javascript:window.opener=null;window.open('','_self');window.close();" class="item" >关闭</a>
	    
	  	<div class="ui simple dropdown item">
			版本回溯 <i class="dropdown icon"></i>
			<div class="menu">
				<a class="item source source_online" source="codeInfo_${codeInfo.id}" href="javascript:;">2015-10-02 11:30:34 ${codeInfo.remark}【ONLINE】</a>
				<textarea id="codeInfo_${codeInfo.id}" style="display:none;" >${codeInfo.source}</textarea>
				<#if codeLogs?exists>
				<div class="divider"></div>
				<#list codeLogs as codeLog>
				<a class="item source" source="codeLogs_${codeLog.id}" href="javascript:;" >2015-10-02 11:30:34 ${codeLog.remark}</a>
				<textarea id="codeLogs_${codeLog.id}" style="display:none;" >${codeLog.source}</textarea>
				</#list>
				</#if>
			</div>
		</div>
	    
	    <a href="javascript:;" class="item right" id="save" >保存</a>
    </div>
</div>

<!-- editor -->
<div id="ace-editor"></div>

<!-- code保存提示 -->
<div class="ui modal" id="CodeSaveTips" >
	<i class="close icon"></i>
	<div class="header">是否执行保存操作?</div>
	<div class="content">
		<form class="ui large form">
			<div class="ui labeled input">
		      	<div class="ui label">备注:</div>
		      	<input type="text" id="codeInfo_remark" placeholder="请输入备注" minlength="6" maxlength="50" >
		    </div>
		    <div class="ui error message"></div>
		</form>
	</div>
	<div class="actions">
		<div class="ui negative button">不</div>
      	<div class="ui positive right labeled icon button">是的<i class="checkmark icon"></i></div>
    </div>
</div>

<@netCommon.commonScript />

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

<script>
<#if !codeInfo?exists>
	alert("CODE ID不存在");
	return;		
</#if>

// Glue Id
var codeInfo_id = '${codeInfo.id}';

// 加载js
require(['requirejs.code.editor']);
</script>

</body>
</html>