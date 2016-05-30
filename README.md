# 敏捷开发平台：xxl-glue
github地址：https://github.com/xuxueli/xxl-glue

git.osc地址：http://git.oschina.net/xuxueli0323/xxl-glue

博客地址(内附使用教程)：http://www.cnblogs.com/xuxueli/p/5115036.html

技术交流群(仅作技术交流)：367260654

# 简介
	推送更行GLUE（抽象且离散的逻辑单元），扩展JVM的动态语言支持；

# 实现原理
	GLUE是一段实现统一接口的Groovy脚本，支持输入输出。通过“Local缓存+广播”的方式进行推送刷新；

# 特点
	1、在线：支持WEB方式在线管理和编辑GLUE；
	2、简单：极简嵌入spring容器；
	3、实时：GLUE变动时主动推送更新并刷新Spring容器中的缓存GLUE；
	
# 新版本1.1.x，新特性
	1.启动预热加载glue，新建glue-app关联表；
	2.【done】删除时，则全站广播，简单remove；
	3.【done】新增“清除缓存”按钮功能，自定义需要通知AppName列表，则全站广播，覆盖更新；
	4.【done】clear cache队列，async清楚缓存；
	5.【dont】缓存Timeout，async清楚缓存，取代懒加载（雪崩：timeout + 懒加载》异步主动加载）；
	6.【done】新建代码时，填充DemoHanr代码；
	tip1.主推功能：配置，规则Util；test页面+unit；local模式/远程模式切换，Local开发，支持同步线上；环境切换，beta/线上；
	
# 应用场景
	场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项
	场景B：托管 “静态方法”，可以将配置解析逻辑一并托管，只关注返回结果即可
	场景B：托管 “抽象且离散的逻辑单元”，可以灵活组装接口和服务（伪服务），作为公共模块
	
# 替代
	分布式缓存：Redis、Memcached（得益于“Local缓存 + 广播”实现方式，可大大减少IO开销）
	分布式配置：Disconf（得益于Groovy的动态语言特性，可支持复杂类型数据接口，即使代码块）

