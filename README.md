# 《分布式逻辑管理平台XXL-GLUE》
## 一、简介

#### 1.1 概述
XXL-GLUE 可以为各种业务平台提供统一的逻辑管理服务, 逻辑的基本单元是GlueHandler。

GlueHandler支持 **在线编译开发, 实时编译，动态推送更新**, 可以方便的嵌入到线上各个业务线的各个业务中。从而扩展部分模块的动态语言特性, 可以节省部分项目编译、打包、部署和重启线上机器等流程消耗, 提高开发效率。

概念解释:
- GlueHandler：即业务中抽象且离散的逻辑单元，本质上是实现统一父接口IGlueHandler的是子类，调用时将会执行其接口中的handle方法并return执行结果数据，代码修改后支持**实时编译，动态推送更新**，因此可以方便的修改线上业务逻辑，而不需要进行打包、部署和上线等操作。
- XXL-GLUE平台：提供逻辑单元（GlueHandler）推送功能，扩展JVM的动态语言支持。提供Wed IDE支持在线开发GlueHandler，并且实时推送更新。

#### 1.2 特性
- 1、交互：提供Wed IDE，支持在线管理和开发GlueHandler；
- 2、简单：Glue可以非常方便的嵌入Spring容器，与现有项目集成方便，上手简单；
- 3、实时：GlueHandler变更时，将会实时推送更新；
- 4、版本：支持50个历史版本的版本回溯；
- 5、注入：支持@Resource和@Autowired两种方式注入Spring容器中服务

#### 1.3 下载
源码地址 (将会在两个git仓库同步发布最新代码)
- [github地址](https://github.com/xuxueli/xxl-glue)
- [git.oschina地址](https://git.oschina.net/xuxueli0323/xxl-glue)

博客地址
- [oschina地址](http://my.oschina.net/xuxueli/blog/732499)

技术交流群(仅作技术交流)：367260654    [![image](http://pub.idqqimg.com/wpa/images/group.png)](http://shang.qq.com/wpa/qunwpa?idkey=4686e3fe01118445c75673a66b4cc6b2c7ce0641528205b6f403c179062b0a52 )

#### 1.4 环境
- Maven3+
- Jdk1.7+
- Tomcat7+
- Mysql5.5+


### 二、快速入门
##### 源码目录介绍

- /db                : 数据库交表脚本位置
- /xxl-glue-admin    : GLUE管理中心
- /xxl-glue-core     : 公共依赖
- /xxl-glue-core-example : 接入XXL-GLUE的Demo项目, 可以参考它来学习如何在项目中接入GLUE

##### 初始化数据库

执行数据库建表脚本: /xxl-glue/db/mysql_xxl_glue.sql


##### 部署"GLUE管理中心"(xxl-glue-admin)

- 配置JDBC地址: 配置文件地址 "/xxl-glue/xxl-glue-admin/src/main/resources/jdbc.properties"
- 配置ActiveMQ地址: 配置文件地址 "/xxl-glue/xxl-glue-admin/src/main/resources/jms.properties"


##### 部署"接入XXL-GLUE的Demo项目"(xxl-glue-core-example)

- 配置JDBC地址: 配置文件地址 "/xxl-glue/xxl-glue-admin/src/main/resources/jdbc.properties"
- 配置ActiveMQ地址: 配置文件地址 "/xxl-glue/xxl-glue-admin/src/main/resources/jms.properties"

##### 开发第一个GlueHandler (Hello World) 

启动"GLUE管理中心"(xxl-glue-admin),登录系统;

登陆GLUE系统，点击新增，填写 “GLUE名称”（该名称是该GLUE项的唯一标示）和简介，确定后即新增一条GLUE。
点击 “编译”按钮，即可进入GlueHandler开发界面，可在该界面开发GlueHandler代码，也可以在IDE中开发完成后粘贴进来，默认已经初始化一个Demo。
每个GlueHandler必须是实现统一父接口GlueHandler的子类；
```
package com.dianping.wed.job;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dianping.glue.core.handler.GlueHandler;

/**
 * 示例GLUE
 * @author xuxueli 2016-5-24 20:30:19
 */
public class DemoHandler implements GlueHandler {
    private static Logger logger = LoggerFactory.getLogger(DemoHandler.class);

    @Override
    public Object handle(Map<String, Object> params) {
        logger.warn("GLUE : Hello World .");
        return 5;
    }
}
```
	
##### 测试

启动"接入XXL-GLUE的Demo项目"(xxl-glue-core-example),访问以下链接测试

    http://localhost:8080/xxl-glue-core-example/code/GLUE名称
    
##### 业务中如何执行自己的GlueHandler

执行以下一行代码即可    
```
	Object result = GlueFactory.glue("glue名称", "glue入参，Map类型");
```


### 三、GlueHandler的三种经典使用场景, 
##### 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项
```
package com.xxl.groovy.example.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项
 * 优点：在线编辑；推送更新；+ 直观；
 * @author xuxueli 2016-4-14 15:36:37
 */
public class DemoHandlerAImpl implements GlueHandler {

	@Override
	public Object handle(Map<String, Object> params) {
		
		// 【列表配置】
		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表
		blackShops.add(15826714);
		blackShops.add(15826715);
		blackShops.add(15826716);
		blackShops.add(15826717);
		blackShops.add(15826718);
		blackShops.add(15826719);
		
		return blackShops;
	}
	
}
```

##### 场景B：托管 “静态方法”，可以将配置解析逻辑一并托管，只关注返回结果即可
```
package com.xxl.groovy.example.service.impl;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * 场景B：托管 “静态方法”
 * 优点：...； + 组件共享；
 * @author xuxueli 2016-4-14 16:07:03
 */
public class DemoHandlerBImpl implements GlueHandler {
	private static final String SHOPID = "shopid";
	
	private static Set<Integer> blackShops = new HashSet<Integer>();
	static {
		blackShops.add(15826714);
		blackShops.add(15826715);
		blackShops.add(15826716);
		blackShops.add(15826717);
		blackShops.add(15826718);
		blackShops.add(15826719);
	}
	
	/**
	 * 商户黑名单判断
	 */
	@Override
	public Object handle(Map<String, Object> params) {
		int shopid = 0;
		if (params!=null && params.get(SHOPID)!=null) {
			shopid = (Integer) params.get(SHOPID);
		}
		if (shopid > 0 && blackShops.contains(shopid)) {
			return true;
		}
		return false;
	}
	
}
```

##### 场景C：托管 “抽象且离散的逻辑单元”，可以灵活组装接口和服务（伪服务），作为公共模块
```
package com.xxl.groovy.example.service.impl;

import java.util.Map;

import com.xxl.glue.core.handler.GlueHandler;

/**
 * 场景B：托管 “抽象且离散的逻辑单元”
 * 优点：...；逻辑封装（伪服务）；
 * @author xuxueli 2016-4-14 16:07:03
 */
public class DemoHandlerCImpl implements GlueHandler {
	private static final String SHOPID = "shopid";
	
	/*
	@Resource
	private AccountService accountService;
	@Autowired
	private AccountService accountServiceB;
	*/
	
	/**
	 * 商户黑名单判断
	 */
	@Override
	public Object handle(Map<String, Object> params) {
		
		/*
		int shopid = 0;
		if (params!=null && params.get(SHOPID)!=null) {
			shopid = (Integer) params.get(SHOPID);
		}
		if (shopid > 0 && accountService.isBlackShop()) {
			return true;
		}
		*/
		
		return false;
	}
	
}
```



### 四、操作指南
##### 第一步：登陆GLUE
![输入图片说明](https://static.oschina.net/uploads/img/201608/14200804_Yuox.png "在这里输入图片标题")

##### 第二步：新建GLUE项
![输入图片说明](https://static.oschina.net/uploads/img/201608/14200827_kkQX.png "在这里输入图片标题")

##### 第三步：GLUE列表
![输入图片说明](https://static.oschina.net/uploads/img/201608/14200852_Q3Mj.png "在这里输入图片标题")

##### 第四步：开发GLUE代码
点击右侧 “编辑” 按钮，进入GLUE开发的Wed IDE界面。默认已经初始化Hello World示例代码，如需开发业务代码，只需要在handle方法中开发即可。

![输入图片说明](https://static.oschina.net/uploads/img/201608/14200932_HAsd.png "在这里输入图片标题")

##### 第五步：一句话执行GlueHandler
首先确定项目中已经接入GLUE（参考上文“三步接入GLUE”，接入非常方便）；
然后执行一行代码即可；

![输入图片说明](https://static.oschina.net/uploads/img/201608/14200956_vxNj.png "在这里输入图片标题")

##### 第六步：推送更新
GlueHandler在第一次加载之后将会缓存在内存中，点击右侧 “清楚缓存” 按钮可以推送更新，填写AppName将会精确定位单个项目进行缓存更新，如果为空则全站广播。

![输入图片说明](https://static.oschina.net/uploads/img/201608/14201023_Y3Be.png "在这里输入图片标题")


### 五、原理剖析

##### Groovy : 用于 Java 虚拟机的一种敏捷的动态语言;
- 1、以强大的Java为基础；
- 2、包含Python、Ruby、Smalltalk等语言强带附加功能，例如动态类型转换、闭包和元编程支持；
- 3、一种成熟的面向对象语言，同时可用作纯粹的校验语言；
- 4、适合与Spring动态语言支持一起使用，因为它专门为JVM设计，充分考虑Java继承；
- 5、与Java代码互操作很容易；


##### 1、系统模块组成
- 1、Glue管理中心：负责管理GLUE，提供GLUE创建、删除，代码编辑等常规功能，以及GLUE刷新功能;
- 2、Glue源码加载服务：GlueHandler源码远程加载服务，Pegion服务；
- 3、Glue执行器：负载加载和实例化GlueHandler，提供公共API。

##### 2、GlueHandler加载步骤
系统中通过 GlueFactory.glue("Glue名称", "Map格式入参") 这一行代码执行对应的GlueHandler，内部执行逻辑如下；
- 1、调用 “loadInstance(name)” 方式，从缓存中加载GlueHandler实例，如果缓存中存在该实例则返回，并执行GlueHandler的 “.handle(params)” 方法，完成一次调用；否则进入步骤2；
- 2、如果缓存中没有GlueHandler实例，则调用 “loadNewInstance(name)” 方法，该方法将会顺序执行以下逻辑：
    - 2.1 执行加载器 “glueLoader.load(name)”  方法加载GlueHandler源码；
    - 2.2 执行 “groovyClassLoader.parseClass(codeSource);” 将源码通过Groovy的类加载器解析为Java类对象实例；
    - 2.3 执行 “injectService(instance)” 方法，在此GlueHandler实例中注入Spring容器中服务；
    - 2.4 加入缓存，返回该GlueHandler；

##### 3、Spring服务注入
支持 “Resource.class” 和 “Autowired.class” 两种方式为GlueHandler输入Spring服务，实现逻辑如下：
- 1、 反射获取GlueHandler的Field数组；
- 2、 遍历Field数组，根据其注解 “@Resource” 和 “@Autowired” 在Spring容器匹配服务（注入规则同Spring默认规则：@Autowired按类型注入；@Resource按照首先名称注入，失败则按照类型注入；）；
- 3、将匹配到的Spring服务注入到该Field中。

##### 4、缓存
Glue中缓存的对象是“groovyClassLoader”解析生成的GlueHandler实例。

GlueHandler缓存支持设置Timeout时间，单位毫秒，缓存失效时将会实例化加载新的GLueHander实例，Timeout设置为-1时将永不失效。

Glue中通过activemq广播消息的方式进行触发主动更新。

##### 5、缓存更新（异步 + 覆盖）
常规缓存更新，通常是通过remove(key)的方式进行缓存清理，然后在下次请求时将会以懒加载的方式进行缓存初始化，但是这样在并发环境中有雪崩的隐患。

因此，GlueHandler采用 “异步（queue + thread）”+“覆盖”的方式进行GlueHandler更新，步骤如下：
- 1、在接收到缓存更新的广播消息时，首先会将待更新的GlueHandler的名称push到待更新队列中；
- 2、异步线程监控待更新队列，获取待更新GlueHandler名称，加载并实例化新GlueHandler实例；
- 3、将新的GlueHandler实例，覆盖缓存中旧的GlueHandler实例，后续调用将会执行新的业务逻辑。

##### 6、灰度更新
GlueHandler通过广播的方式进行推送更新，在推送广播消息时支持输入待刷新该GlueHandler的项目名列表，只有匹配到的项目才会对本项目中GlueHandler进行覆盖更新，否则则忽视该条广播消息。

##### 7、版本回溯
GlueHandler的每次更新都会进行对上个版本的代码进行备份，备份数量支持配置，原则上无上限。
同时，在Web IDE界面上，可以查看到所有的备份记录，并且可以方便的进行版本回退。

##### 8、预热
GlueHandler创建时和项目关联起来，这样在项目启动时会主动加载关联到的GlueHandler，避免懒加载引起的并发问题。

##### 9、避免因Permanet Generation空间被占满引起的Full GC
PermanetGeneration中存放的为一些class的信息等，当系统中要加载的类、反射的类和调用的方法较多时，Permanet Generation可能会被占满。

GLUE底层基于Groovy实现，Groovy之前使用时曾经出现过频繁Full GC的问题，原因如下：

系统在执行 “groovy.lang.GroovyClassLoader.parseClass(groovyScript)” 进行groovy代码解析时，Groovy为了保证解析后执行的都是最新的脚本内容，每进行一次解析都会生成一次新命名的Class文件，如下图：

![输入图片说明](https://static.oschina.net/uploads/img/201608/14200626_QHdj.jpg "在这里输入图片标题")

因此，如果Groovy类加载器设置为静态，当对同一段脚本均多次执行该方法时，会导致 “GroovyClassLoader” 装载的Class越来越多，从而导致PermGen被用满。

为了避免出现类似问题，GLUE做了以下几点优化。
- 1、针对每个GlueHandler解析后生成的Java对象实例做缓存，而不是代码本身做缓存；
- 2、仅仅在接收到清除缓存的广播时解析生成新的GlueHandler实例对象，避免groovy的频繁解析，减少Class装载频率；
- 3、周期性的异步刷新类加载器，避免因全局类加载器装载Class过多且不及时卸载导致的PermGen被用满。


## 六、历史版本
#### 版本1.0.0
- 1、交互：提供Wed IDE，支持在线管理和开发GlueHandler；
- 2、简单：Glue可以非常方便的嵌入Spring容器，与现有项目集成方便，上手简单；
- 3、实时：GlueHandler变更时，将会实时推送更新；
- 4、版本：支持50个历史版本的版本回溯；
- 5、注入：支持@Resource和@Autowired两种方式注入Spring容器中服务

#### 版本1.1.0
- 1、重要重构;
- 2、交互优化;

## 七、其他

#### 7.1 报告问题
XXL-GLUE托管在Github上，如有问题可在 [ISSUES](https://github.com/xuxueli/xxl-glue/issues) 上提问，也可以加入技术交流群(仅作技术交流)：367260654

#### 7.2 接入登记
更多接入公司，欢迎在github [登记](https://github.com/xuxueli/xxl-glue/issues/1 )