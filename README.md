- github地址：https://github.com/xuxueli/xxl-glue
- git.osc地址：http://git.oschina.net/xuxueli0323/xxl-glue
- 博客地址：http://www.cnblogs.com/xuxueli/p/5115036.html

### 一、背景
在日常业务开发过程中，尤其是后端业务开发过程中，我们经常会遇到一些逻辑变动相对频繁的需求。而每一次需求变更，都意味着一次项目打包，部署，发布线上机器……流程多，耗时久。这一方面降低了开发效率，另一方面会影响线上服务的稳定性。

因此我们设想，是否可以**将频繁变动的业务逻辑单元抽象成 “变量” **托管起来，每次逻辑修改后支持**实时编译，动态推送更新**，就像一块灵活的胶水。

百度给出的其中一个解决方案是“Disconf”，我们可以将配置信息抽象成 “变量（Disconf配置）” 交给Disconf维护，Disconf负责维护该变量并且实时推送更新，从而，当Disconf配置更新时我们可以避免冗余的“项目打包，部署，发布线上机器……”。

但是，Disconf作为配置管理系统，主要功能在于推送**基础类型数据**的配置更新。

我们希望新托管平台，不仅支持托管**基础类型数据**（阈值，开关，黑白名单），而且支持**复杂集合类型数据**（List、Set、Map甚至Java对象），同时支持托管**业务代码**（如线上Job“婚宴头图手动绑定”，其业务代码已接入GLUE；此JOB从开发、测试到线上运行，共计耗时五分钟，不需要打包，部署，发布线上机器……）。
GLUE目的在此，它就像一块业务系统中的一块胶水，针对变化的业务场景进行粘合。


### 二、简介
GLUE：它提供逻辑单元（GlueHandler）推送功能，扩展JVM的动态语言支持。提供Wed IDE支持在线开发GlueHandler，并且实时推送更新。
GlueHandler：即业务中抽象且离散的逻辑单元，本质上是实现统一父接口IGlueHandler的是子类，调用时将会执行其接口中的handle方法并return执行结果数据，代码修改后支持**实时编译，动态推送更新**，因此可以方便的修改线上业务逻辑，而不需要进行打包、部署和上线等操作。

### 三、特点
- 1、交互：提供Wed IDE，支持在线管理和开发GlueHandler；
- 2、简单：Glue可以非常方便的嵌入Spring容器，与现有项目集成方便，上手简单；
- 3、实时：GlueHandler变更时，将会实时推送更新；
- 4、版本：支持50个历史版本的版本回溯；
- 5、注入：支持@Resource和@Autowired两种方式注入Spring容器中服务

### 四、原理剖析

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

![](http://w4c.dp/doc/data/image/201606/04/52686_Yzny_6828.jpg)

因此，如果Groovy类加载器设置为静态，当对同一段脚本均多次执行该方法时，会导致 “GroovyClassLoader” 装载的Class越来越多，从而导致PermGen被用满。

为了避免出现类似问题，GLUE做了以下几点优化。
- 1、针对每个GlueHandler解析后生成的Java对象实例做缓存，而不是代码本身做缓存；
- 2、仅仅在接收到清除缓存的广播时解析生成新的GlueHandler实例对象，避免groovy的频繁解析，减少Class装载频率；
- 3、周期性的异步刷新类加载器，避免因全局类加载器装载Class过多且不及时卸载导致的PermGen被用满。


### 五、使用场景
##### 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项
![](http://w4c.dp/doc/data/image/201606/02/26191_fVgQ_7133.png)

##### 场景B：托管 “静态方法”，可以将配置解析逻辑一并托管，只关注返回结果即可
![](http://w4c.dp/doc/data/image/201606/02/26260_jukh_5088.png)

##### 场景C：托管 “抽象且离散的逻辑单元”，可以灵活组装接口和服务（伪服务），作为公共模块
![](http://w4c.dp/doc/data/image/201606/02/26284_EwLn_3847.png)

### 六、地址
线上地址：http://glue.dp/
BETA地址：http://10.66.62.77:8080/ 
账号/密码：wed/wed

### 七、三步接入GLUE
##### 第一步：引入glue-core依赖
```
			<!-- GLUE源码加载器依赖 -->
			<dependency>
				<groupId>com.dianping</groupId>
				<artifactId>wed-platform-api</artifactId>
				<version>1.0.0.43-SNAPSHOT</version>
			</dependency>
			<!-- GLUE依赖 -->
			<dependency>
				<groupId>com.dianping</groupId>
				<artifactId>glue-core</artifactId>
				<version>1.0.1</version>
			</dependency>
```
##### 第二步：配置GlueFactory
```
			<!-- GLUE源码加载器 -->
			<bean id="glueLoaderService" class="com.dianping.dpsf.spring.ProxyBeanFactory" init-method="init">
				<property name="serviceName" value="http://service.dianping.com/wedPlatformService/glueLoaderService_1.0.0"/>
				<property name="iface" value="com.dianping.glue.core.GlueLoader"/>
				<property name="serialize" value="hessian"/>
				<property name="callMethod" value="sync"/>
				<property name="timeout" value="5000"/>
			</bean>
			<!-- GLUE -->
			<bean id="glueFactory" class="com.dianping.glue.core.GlueFactory" init-method="init" >
				<property name="cacheTimeout" value="300000" />
				<property name="appName" value="glue-core-example" />
				<property name="glueLoader" ref="glueLoaderService" />
			</bean>
			说明：
				cacheTimeout： GLUE本地缓存时间，如果选择广播更新，该值可适当调大，否则应该调整为较小的值，保证及时更新，-1表示永久缓存；
				appName：GLUE实例名称，“清除缓存”时，可填写AppName进行灰度刷新；
				glueLoader	： GLUE源码加载器服务；
```

##### 第三步：使用Glue
```
	Object result = GlueFactory.glue("glue名称", "glue入参，Map类型");
```

### 八、Hello World （开发第一个GlueHandler）
登陆GLUE系统，点击新增，填写 “GLUE名称”（该名称是该GLUE项的唯一标示）和简介，确定后即新增一条GLUE。
点击 “编译”按钮，即可进入GlueHandler开发界面，可在该界面开发GlueHandler代码，也可以在IDE中开发完成后粘贴进来，默认已经实例化一个Demo。
每个GlueHandler必须是实现统一父接口GlueHandler的是子类；
	
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

### 九、操作指南
##### 第一步：登陆GLUE
![](http://w4c.dp/doc/data/image/201606/01/46013_uhnC_4515.png)

##### 第二步：新建GLUE项
![](http://w4c.dp/doc/data/image/201606/01/46116_zZKR_4974.png)

##### 第三步：GLUE列表
![](http://w4c.dp/doc/data/image/201606/01/46314_WOEL_8054.png)

##### 第四步：开发GLUE代码
点击右侧 “编辑” 按钮，进入GLUE开发的Wed IDE界面。默认已经初始化Hello World示例代码，如需开发业务代码，只需要在handle方法中开发即可。

![](http://w4c.dp/doc/data/image/201606/01/46231_SPMV_7394.png)

##### 第五步：一句话执行GlueHandler
首先确定项目中已经接入GLUE（参考上文“三步接入GLUE”，接入非常方便）；
然后执行一行代码即可；

![](http://w4c.dp/doc/data/image/201606/01/47416_igSv_9253.png)

##### 第六步：推送更新
GlueHandler在第一次加载之后将会缓存在内存中，点击右侧 “清楚缓存” 按钮可以推送更新，填写AppName将会精确定位单个项目进行缓存更新，如果为空则全站广播。

![](http://w4c.dp/doc/data/image/201606/01/46548_HOQi_1227.png)