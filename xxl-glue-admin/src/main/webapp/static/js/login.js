'use strict';	// 开启严格模式以后，一些js糟糕的特性都会被禁用:检查对象中的重复键/未声明变量/重复的参数...
define(['jquery', 'semantic-comalert', 'jquery.validate', 'semantic', 'semantic-ui-form', 'semantic-ui-transition', 'common'], function($, ComAlert) {
    //alert($().jquery);
	
    $('.ui.form').form({
		fields: {
			email: {
				identifier  : 'email',
              	rules: [
                	{
                  		type   : 'empty',
                  		prompt : '请输入邮箱'
                	},
                	{
                  		type   : 'email',
                  		prompt : '请您输入正确的邮箱'
                	}
              	]
            },
            password: {
              	identifier  : 'password',
              	rules: [
                	{
                  		type   : 'empty',
                  		prompt : '请输入密码'
                	},
                	{
                  		type   : 'length[6]',
                  		prompt : '请您输入长度超过6位的密码'
                	}
              	]
			}
		},
		onSuccess: function(){
			$.ajax({
				type : 'POST',
				url : base_url + 'login',
				data : $("#loginForm").serialize(),
				dataType : "json",
				success : function(data){
					if (data.code == 200) {
						ComAlert.alert('登陆成功', function(){
							window.location.href = base_url;
						});
					} else {
						ComAlert.alert(data.msg);
					}
				}
			});
			return false;	// 避免默认return true表单提交
		}
	});
	
    return '200';	// return则支持返回this引用，否则不支持
});