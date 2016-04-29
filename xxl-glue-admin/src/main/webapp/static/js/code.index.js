'use strict';
define(['jquery', 'semantic-comalert', 'moment', 'datatables-jquery', 'jquery.validate', 'semantic', 'semantic-ui-form', 'semantic-ui-transition'], function($, ComAlert, moment ) {
	
	// init date tables
	var codeTable = $("#code_list").dataTable({
		"deferRender": true,
		"processing" : true, 
	    "serverSide": true,
		"ajax": {
	        url: base_url + "/code/pageList" ,
	        data : function ( d ) {
                d.name = $('#codeName').val()
            }
	    },
	    "searching": false,
	    "ordering": true,
	    //"scrollX": true,  // X轴滚动条，强制性固定长度
	    "columns": [
	                { "data": 'id', "bSortable": false, "visible" : true},
	                { "data": 'name', "bSortable": false},
	                { "data": 'source', "bSortable": false, "visible" : false},
	                { "data": 'remark', "bSortable": false},
	                { 
	                	"data": 'addTime', 
	                	"bSortable": false, 
	                	"render": function ( data, type, row ) {
	                		return data?moment(new Date(data)).format("YYYY-MM-DD HH:mm:ss"):"";
	                	}
	                },
	                { 
	                	"data": 'updateTime',
	                	"bSortable": false,
	                	"render": function ( data, type, row ) {
	                		return data?moment(new Date(data)).format("YYYY-MM-DD HH:mm:ss"):"";
	                	}
	                }
	            ],
        "columnDefs": [
                     {
                    	 "targets": 6,
                         "data": "id",
                         "render": function ( data, type, row ) {
                        	 $('#delCode_' + row.id).on('click', function(){
                        		 ComAlert.confirm("确定要进行删除操作，该操作不可恢复",
                    			     function(){
                    				 	$.ajax({
                    						type : 'POST',
                    						url : base_url + '/code/delCode',
                    						data : {
                    							id : row.id
                    						},
                    						dataType : "json",
                    						success : function(data){
                    							if (data.code == 200) {
                    								ComAlert.alert("删除成功", function(){
                    									codeTable.fnDraw();
                    		     					});
                    							} else {
                    								ComAlert.alert(data.msg);
                    							}
                    						}
                    					});
                    				}
                        	 	);
                        	 });
                        	 var editUrl = base_url + "code/codeSourceEditor?id=" + row.id;
                        	 var html = '<button type="button" class="yellow ui tiny button" onclick="javascript:window.open(\''+editUrl+'\');">编辑</button>' + 
                             '<button type="button" class="red ui tiny button delCode" _id="'+ row.id +'" >删除</button>';
                             return html;
                         }
                     }
                 ],
		"language" : {
			"sProcessing" : "处理中...",
			"sLengthMenu" : "每页 _MENU_ 条记录",
			"sZeroRecords" : "没有匹配结果",
			"sInfo" : "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
			"sInfoEmpty" : "无记录",
			"sInfoFiltered" : "(由 _MAX_ 项结果过滤)",
			"sInfoPostFix" : "",
			"sSearch" : "搜索:",
			"sUrl" : "",
			"sEmptyTable" : "表中数据为空",
			"sLoadingRecords" : "载入中...",
			"sInfoThousands" : ",",
			"oPaginate" : {
				"sFirst" : "首页",
				"sPrevious" : "上页",
				"sNext" : "下页",
				"sLast" : "末页"
			},
			"oAria" : {
				"sSortAscending" : ": 以升序排列此列",
				"sSortDescending" : ": 以降序排列此列"
			}
		}
	});
	
	// 搜索按钮
	$('#searchBtn').on('click', function(){
		codeTable.fnDraw();
	});
	
	// 搜索按钮，回车监听
	$('#codeName').bind('keyup', function(event) {
		if (event.keyCode == "13") {
			$('#searchBtn').click();
		}
	});
	
	// 删除
	$('#code_list').on('click', '.delCode', function(){
		var _id = $(this).attr('_id');
		ComAlert.confirm("确定要进行删除操作，该操作不可恢复",
			function(){
			 	$.ajax({
					type : 'POST',
					url : base_url + '/code/delCode',
					data : {
						id : _id
					},
					dataType : "json",
					success : function(data){
						if (data.code == 200) {
							ComAlert.alert("删除成功", function(){
								codeTable.fnDraw();
	     					});
						} else {
							ComAlert.alert(data.msg);
						}
					}
			 	});
			}
		);
	});
	
	// 新增
	$('#addBtn').on('click', function(){
		$('#addModal').modal({
			closable  : false,
			duration : 100,
			allowMultiple: true,
			onApprove : function(){	},
		    onDeny : function() {	}
		}).modal('show');
	});
	
	// 新增form
	$('#addModal .form').form({
		fields: {
			name: {
				identifier  : 'name',
              	rules: [
                	{
                  		type   : 'empty',
                  		prompt : '请输入“Code名称”'
                	},
                	{
                		type   : 'length[6]',
                  		prompt : '“Code名称”长度应该大于6位'
                	},
                	{
                        type   : 'regExp[/^[a-zA-Z][a-zA-Z0-9_]*$/]',
                        prompt : '“Code名称”只支持英文字母开头，且只允许由英文字母、数字和下划线组成'
                	}
              	]
            },
            remark: {
              	identifier  : 'remark',
              	rules: [
                	{
                  		type   : 'empty',
                  		prompt : '请输入“简介”'
                	},
                	{
                  		type   : 'length[6]',
                  		prompt : '“简介”长度应该大于6位'
                	}
              	]
			}
		},
		onSuccess: function(){
			$.ajax({
				type : 'POST',
				url : base_url + 'code/addCode',
				data : $("#addModal .form").serialize(),
				dataType : "json",
				success : function(data){
					if (data.code == 200) {
						ComAlert.alert('保存成功', function(){
							codeTable.fnDraw();
						});
					} else {
						//ComAlert.alert(data.msg);
						$('#addModal .form').removeClass('success').addClass('error');
						$('#addModal .form .error').html('<ul class="list"><li>'+ data.msg +'</li></ul>');
					}
				}
			});
			return false;	// 避免默认return true表单提交
		}
	});
	
    return '200';
});