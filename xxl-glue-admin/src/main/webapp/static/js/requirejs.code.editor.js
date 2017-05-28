"use strict";
define(function(require, exports, module) {

	// init ace-editor
	//require("source-code-pro");
	var ace = require("ace/ace");
	var editor = ace.edit("ace-editor");
	editor.setTheme("ace/theme/idle_fingers");
	editor.getSession().setMode("ace/mode/groovy");
	
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
				if (codeInfo_remark.length < 6|| codeInfo_remark.length > 100) {
					$('#CodeSaveTips .form').removeClass('success').addClass('error');
					$('#CodeSaveTips .form .error').html('<ul class="list"><li>备注长度应该在6至100之间</li></ul>');
					return false;
				}
				
				var code = editor.getSession().getValue();
				// or session.getValue
				//console.log(code);
				
				$.ajax({
					type : 'POST',
					url : base_url + 'code/updateCodeSource',
					data : {
						'id' : codeInfo_id,
						'remark' : codeInfo_remark,
						'source' : code
					},
					dataType : "json",
					success : function(data){
						if (data.code == 200) {
							ComAlert.alert('提交成功', function(){
								$(window).unbind('beforeunload');
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
	$(".source").on('click', function(){
		editor.setValue($('#'+$(this).attr('source')).val());
		//editor.gotoLine(editor.session.getLength());
		editor.gotoLine(1);
	});
	
	// Glue source初始化
	$('.source_online').click();
	
	// listener Ctrl + S
	editor.commands.addCommand({
	    name: 'Ctrl-S',
	    bindKey: {win: 'Ctrl-S',  mac: 'Command-S'},
	    exec: function(editor) {
	    	$("#save").click();
	    },
	    readOnly: false // false if this command should not apply in readOnly mode
	});
	
	// before upload
	$(window).bind('beforeunload',function(){
		return 'Glue尚未保存，确定离开Glue编辑器？';
	});
	
});
