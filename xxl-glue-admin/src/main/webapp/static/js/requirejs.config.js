require.config({
	//baseUrl: '',	// 当前文件为根路径
    paths: {
        'ace'						:	'../plugins/ace/'
    },
    waitSeconds : 0,	// 等待超时, 默认7秒，0时表示放弃超时
	urlArgs: "bust=" + (new Date()).getTime()	// 版本号, 防止缓存
});