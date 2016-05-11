require.config({
	//baseUrl: '',	// 当前文件为根路径
	// 非AMD模块输出，将非标准的AMD模块"垫"成可用的模块
	shim: {
		'jquery.validate': ['jquery'],
		'semantic':['jquery'],
		'semantic-ui-modal': ['semantic'],
		'semantic-ui-form': ['semantic'],
		'semantic-ui-transition': {deps: ['semantic'], exports: 'semantic-ui-transition' },	// 设置依赖文件和别名
		'datatables-jquery' : ['jquery']
	},
    paths: {
        'jquery'					:	'../plugins/jquery/jquery.2.1.4.min',
        'jquery.validate'			: 	'../plugins/jquery/jquery.validate.1.13.1.min',
        'semantic'					:	'../plugins/semantic-ui/dist/semantic.min',
        'semantic-ui-modal' 		: 	'../plugins/semantic-ui/dist/components/modal',
        'semantic-ui-form' 			: 	'../plugins/semantic-ui/dist/components/form',
        'semantic-ui-transition' 	:	'../plugins/semantic-ui/dist/components/transition',
        'semantic-comalert'			:	'../js/semantic-comalert',
        'ace'						:	'../plugins/ace/',						// path支持设置整个目录
        'source-code-pro'			:	'../plugins/jquery/jquery.2.1.4.min',	// path支持设置单个文件
        'datatables-jquery'			:	'../plugins/datatables/media/js/jquery.dataTables.min',
        'moment'					:	'../plugins/moment/moment.min',
        'pace'						:	'../plugins/pace/pace.min',
    },
    waitSeconds : 0,	// 等待超时, 默认7秒，0时表示放弃超时
	urlArgs: "bust=" + (new Date()).getTime()	// 版本号, 防止缓存
});