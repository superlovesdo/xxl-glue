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
				<a class="item loadCodeInfo" href="javascript:;">2015-10-02 11:30:34 ${codeInfo.remark} 【线上】</a>
				<a class="item loadCodeLog" logId="1" href="javascript:;" >2015-10-02 11:30:34 更新线上配置</a>
				<a class="item loadCodeLog" logId="2" href="javascript:;" >2015-10-02 11:30:34 新版本上线</a>
				<div class="divider"></div>
			</div>
		</div>
	    
	    <a href="javascript:;" class="item right" id="save" >保存</a>
    </div>
</div>

<!-- editor -->
<div id="ace-editor"></div>
<textarea rows="3" cols="20" id="codeInfo_source" style="display:none;" >${codeInfo.source}</textarea>

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

<script src="${request.contextPath}/static/plugins/requirejs/requirejs.2.1.22.min.js" data-main="${request.contextPath}/static/js/requirejs.config" ></script>
<script>
<#if !codeInfo?exists>
	alert("CODE ID不存在");
	return;		
</#if>

// 更新字段
var codeInfo_id = '${codeInfo.id}';
var codeInfo_source = document.getElementById('codeInfo_source').value;
var codeInfo_remark = "${codeInfo.remark}";

// 加载js
var base_url = '${request.contextPath}/';
require(['code.editor']);
</script>

</body>
</html>