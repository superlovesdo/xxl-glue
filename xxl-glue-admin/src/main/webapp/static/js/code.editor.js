"use strict";
define(function(require, exports, module) {
	var jquery = require("jquery");
	var ComAlert = require('semantic-comalert');
	
	// init ace-editor
	//require("source-code-pro");
	var ace = require("ace/ace");
	var editor = ace.edit("ace-editor");
	editor.setTheme("ace/theme/idle_fingers");
	editor.getSession().setMode("ace/mode/groovy");
	
	// 初始化页面
	editor.setValue(codeInfo_source);
	
	$("#save").on('click', function(){
		
		$('#CodeSaveTips').modal({
			closable  : false,
			duration : 100,
			onApprove : function() {
				
				var codeInfo_remark = $("#codeInfo_remark").val();
				
				// remark check
				$('#CodeSaveTips .form').removeClass('error').addClass('success');
				if (!codeInfo_remark) {
					$('#CodeSaveTips .form').removeClass('success').addClass('error');
					$('#CodeSaveTips .form .error').html('<ul class="list"><li>请输入备注</li></ul>');
					return false;
				}
				if (codeInfo_remark.length < 6) {
					$('#CodeSaveTips .form').removeClass('success').addClass('error');
					$('#CodeSaveTips .form .error').html('<ul class="list"><li>备注长度至少为6位</li></ul>');
					return false;
				}
				
				var code = editor.getSession().getValue();
				// or session.getValue
				console.log(code);
				
				$.ajax({
					type : 'POST',
					url : base_url + 'code/updateCodeSource',
					data : {
						'id' : codeInfo_id,
						'source' : code,
						'remark' : codeInfo_remark
					},
					dataType : "json",
					success : function(data){
						if (data.code == 200) {
							ComAlert.alert('提交成功', function(){
								window.location.reload();
							});
							// or session.setValue
						} else {
							ComAlert.alert(data.msg);
						}
					}
				});
		    }
		}).modal('show');
	});
	
	// 回溯-线上
	$(".loadCodeInfo").on('click', function(){
		editor.setValue(codeInfo_source);
	});
	
});
