/*
Navicat MySQL Data Transfer

Source Server         : meme-127.0.0.1
Source Server Version : 50544
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-04-14 20:57:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xxl_glue_code_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_code_info`;
CREATE TABLE `xxl_glue_code_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `source` text,
  `remark` varchar(255) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_glue_code_info
-- ----------------------------
INSERT INTO `xxl_glue_code_info` VALUES ('11', 'DemoHandlerCImpl', 'package com.xxl.groovy.example.service.impl;\r\n\r\nimport java.util.Map;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * 场景B：托管 “抽象且离散的逻辑单元”\r\n * 优点：...；逻辑封装（伪服务）；\r\n * @author xuxueli 2016-4-14 16:07:03\r\n */\r\npublic class DemoHandlerCImpl implements GlueHandler {\r\n	private static final String SHOPID = \"shopid\";\r\n	\r\n	/*\r\n	@Resource\r\n	private AccountService accountService;\r\n	@Autowired\r\n	private AccountService accountServiceB;\r\n	*/\r\n	\r\n	/**\r\n	 * 搜索推荐的商户\r\n	 */\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n		\r\n		/*\r\n		int shopid = 0;\r\n		if (params!=null && params.get(SHOPID)!=null) {\r\n			shopid = (Integer) params.get(SHOPID);\r\n		}\r\n		if (shopid > 0 && accountService.isBlackShop()) {\r\n			return true;\r\n		}\r\n		*/\r\n		\r\n		return false;\r\n	}\r\n	\r\n}\r\n', '商户黑名单，服务模块，初始化', '2016-04-14 19:55:26', '2016-04-14 20:26:57');
INSERT INTO `xxl_glue_code_info` VALUES ('12', 'DemoHandlerBImpl', 'package com.xxl.groovy.example.service.impl;\r\n\r\nimport java.util.HashSet;\r\nimport java.util.Map;\r\nimport java.util.Set;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * 场景B：托管 “静态方法”，\r\n * 优点：...； + 组件共享；\r\n * @author xuxueli 2016-4-14 16:07:03\r\n */\r\npublic class DemoHandlerBImpl implements GlueHandler {\r\n	private static final String SHOPID = \"shopid\";\r\n	\r\n	private static Set<Integer> blackShops = new HashSet<Integer>();\r\n	static {\r\n		blackShops.add(15826714);\r\n		blackShops.add(15826715);\r\n		blackShops.add(15826716);\r\n		blackShops.add(15826717);\r\n		blackShops.add(15826718);\r\n		blackShops.add(15826719);\r\n	}\r\n	\r\n	/**\r\n	 * 商户黑名单判断\r\n	 */\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n		int shopid = 0;\r\n		if (params!=null && params.get(SHOPID)!=null) {\r\n			shopid = (Integer) params.get(SHOPID);\r\n		}\r\n		if (shopid > 0 && blackShops.contains(shopid)) {\r\n			return true;\r\n		}\r\n		return false;\r\n	}\r\n	\r\n}\r\n', '黑名单组件，初始化', '2016-04-14 19:55:54', '2016-04-14 19:56:55');
INSERT INTO `xxl_glue_code_info` VALUES ('13', 'DemoHandlerAImpl', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Arrays;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport com.xxl.glue.core.handler.GlueHandler;\n\n/**\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\n * 优点：在线编辑；推送更新；+ 直观；\n * @author xuxueli 2016-4-14 15:36:37\n */\npublic class DemoHandlerAImpl implements GlueHandler {\n\n	@Override\n	public Object handle(Map<String, Object> params) {\n		\n		// 【基础类型配置】\n		boolean ifOpen = true;															// 开关\n		int smsLimitCount = 5;															// 短信发送次数阀值\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\n		\n		// 【列表配置】\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\n		blackShops.add(15826714);\n		blackShops.add(15826715);\n		blackShops.add(15826716);\n		blackShops.add(15826717);\n		blackShops.add(15826718);\n		blackShops.add(15826719);\n		\n		// 【KV配置】\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\n		emailDispatch.put(555, \"淘宝\");\n		emailDispatch.put(666, \"天猫\");\n		emailDispatch.put(777, \"聚划算\");\n		\n		// 【复杂集合配置】\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\n		\n		return smsLimitCount;\n	}\n	\n}\n', '修改阀值返回值', '2016-04-14 19:56:15', '2016-04-14 20:27:52');

-- ----------------------------
-- Table structure for xxl_glue_code_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_code_log`;
CREATE TABLE `xxl_glue_code_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `source` text,
  `remark` varchar(255) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_glue_code_log
-- ----------------------------
INSERT INTO `xxl_glue_code_log` VALUES ('44', 'DemoHandlerAImpl', 'package com.xxl.groovy.example.service.impl;\r\n\r\nimport java.util.Arrays;\r\nimport java.util.HashMap;\r\nimport java.util.HashSet;\r\nimport java.util.List;\r\nimport java.util.Map;\r\nimport java.util.Set;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\r\n * 优点：在线编辑；推送更新；+ 直观；\r\n * @author xuxueli 2016-4-14 15:36:37\r\n */\r\npublic class DemoHandlerAImpl implements GlueHandler {\r\n\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n		\r\n		// 【基础类型配置】\r\n		boolean ifOpen = true;															// 开关\r\n		int smsLimitCount = 3;															// 短信发送次数阀值\r\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\r\n		\r\n		// 【列表配置】\r\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\r\n		blackShops.add(15826714);\r\n		blackShops.add(15826715);\r\n		blackShops.add(15826716);\r\n		blackShops.add(15826717);\r\n		blackShops.add(15826718);\r\n		blackShops.add(15826719);\r\n		\r\n		// 【KV配置】\r\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\r\n		emailDispatch.put(555, \"淘宝\");\r\n		emailDispatch.put(666, \"天猫\");\r\n		emailDispatch.put(777, \"聚划算\");\r\n		\r\n		// 【复杂集合配置】\r\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\r\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\r\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\r\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\r\n		\r\n		return smsLimitCount;\r\n	}\r\n	\r\n}\r\n', '黑名单列表，初始化', '2016-04-14 20:27:52', '2016-04-14 20:27:52');
