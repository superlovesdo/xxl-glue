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
<script>
<#if !codeInfo?exists>
	alert("CODE ID不存在");
	return;		
</#if>

// Glue Id
var codeInfo_id = '${codeInfo.id}';

// 加载js
require(['code.editor']);
</script>

</body>
</html>