/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50631
 Source Host           : 127.0.0.1
 Source Database       : xxl-glue

 Target Server Type    : MySQL
 Target Server Version : 50631
 File Encoding         : utf-8

 Date: 05/29/2017 21:14:52 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `xxl_glue_codelog`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_codelog`;
CREATE TABLE `xxl_glue_codelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `glue_id` int(11) NOT NULL COMMENT 'GLUE主键ID',
  `remark` varchar(128) NOT NULL,
  `source` text,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `xxl_glue_codelog`
-- ----------------------------
BEGIN;
INSERT INTO `xxl_glue_codelog` VALUES ('48', '0', '修改阀值返回值[backup for 编辑更新]', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Arrays;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport com.xxl.glue.core.handler.GlueHandler;\n\n/**\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\n * 优点：在线编辑；推送更新；+ 直观；\n * @author xuxueli 2016-4-14 15:36:37\n */\npublic class DemoHandlerAImpl implements GlueHandler {\n\n	@Override\n	public Object handle(Map<String, Object> params) {\n		\n		// 【基础类型配置】\n		boolean ifOpen = true;															// 开关\n		int smsLimitCount = 5;															// 短信发送次数阀值\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\n		\n		// 【列表配置】\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\n		blackShops.add(15826714);\n		blackShops.add(15826715);\n		blackShops.add(15826716);\n		blackShops.add(15826717);\n		blackShops.add(15826718);\n		blackShops.add(15826719);\n		\n		// 【KV配置】\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\n		emailDispatch.put(555, \"淘宝\");\n		emailDispatch.put(666, \"天猫\");\n		emailDispatch.put(777, \"聚划算\");\n		\n		// 【复杂集合配置】\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\n		\n		return smsLimitCount;\n	}\n	\n}\n', '2016-05-18 16:59:35', '2016-05-18 16:59:35'), ('49', '0', '返回值修改为5[backup for 编辑更新]', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Arrays;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport com.xxl.glue.core.handler.GlueHandler;\n\n/**\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\n * 优点：在线编辑；推送更新；+ 直观；\n * @author xuxueli 2016-4-14 15:36:37\n */\npublic class DemoHandlerAImpl implements GlueHandler {\n\n	@Override\n	public Object handle(Map<String, Object> params) {\n		\n		// 【基础类型配置】\n		boolean ifOpen = true;															// 开关\n		int smsLimitCount = 6;															// 短信发送次数阀值\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\n		\n		// 【列表配置】\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\n		blackShops.add(15826714);\n		blackShops.add(15826715);\n		blackShops.add(15826716);\n		blackShops.add(15826717);\n		blackShops.add(15826718);\n		blackShops.add(15826719);\n		\n		// 【KV配置】\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\n		emailDispatch.put(555, \"淘宝\");\n		emailDispatch.put(666, \"天猫\");\n		emailDispatch.put(777, \"聚划算\");\n		\n		// 【复杂集合配置】\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\n		\n		return smsLimitCount;\n	}\n	\n}\n', '2016-05-18 17:00:09', '2016-05-18 17:00:09'), ('50', '0', '返回值修改为7[backup for 编辑更新]', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Arrays;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport com.xxl.glue.core.handler.GlueHandler;\n\n/**\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\n * 优点：在线编辑；推送更新；+ 直观；\n * @author xuxueli 2016-4-14 15:36:37\n */\npublic class DemoHandlerAImpl implements GlueHandler {\n\n	@Override\n	public Object handle(Map<String, Object> params) {\n		\n		// 【基础类型配置】\n		boolean ifOpen = true;															// 开关\n		int smsLimitCount = 7;															// 短信发送次数阀值\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\n		\n		// 【列表配置】\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\n		blackShops.add(15826714);\n		blackShops.add(15826715);\n		blackShops.add(15826716);\n		blackShops.add(15826717);\n		blackShops.add(15826718);\n		blackShops.add(15826719);\n		\n		// 【KV配置】\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\n		emailDispatch.put(555, \"淘宝\");\n		emailDispatch.put(666, \"天猫\");\n		emailDispatch.put(777, \"聚划算\");\n		\n		// 【复杂集合配置】\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\n		\n		return smsLimitCount;\n	}\n	\n}\n', '2017-05-28 23:44:07', '2017-05-28 23:44:07');
COMMIT;

-- ----------------------------
--  Table structure for `xxl_glue_glueinfo`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_glueinfo`;
CREATE TABLE `xxl_glue_glueinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `project_id` int(11) NOT NULL COMMENT '项目ID',
  `name` varchar(128) NOT NULL,
  `about` varchar(128) NOT NULL,
  `source` text,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_name` (`name`) USING BTREE,
  KEY `u_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `xxl_glue_glueinfo`
-- ----------------------------
BEGIN;
INSERT INTO `xxl_glue_glueinfo` VALUES ('11', '0', 'DemoHandlerCImpl', '略', 'package com.xxl.groovy.example.service.impl;\r\n\r\nimport java.util.Map;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * 场景B：托管 “抽象且离散的逻辑单元”\r\n * 优点：...；逻辑封装（伪服务）；\r\n * @author xuxueli 2016-4-14 16:07:03\r\n */\r\npublic class DemoHandlerCImpl implements GlueHandler {\r\n	private static final String SHOPID = \"shopid\";\r\n	\r\n	/*\r\n	@Resource\r\n	private AccountService accountService;\r\n	@Autowired\r\n	private AccountService accountServiceB;\r\n	*/\r\n	\r\n	/**\r\n	 * 搜索推荐的商户\r\n	 */\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n		\r\n		/*\r\n		int shopid = 0;\r\n		if (params!=null && params.get(SHOPID)!=null) {\r\n			shopid = (Integer) params.get(SHOPID);\r\n		}\r\n		if (shopid > 0 && accountService.isBlackShop()) {\r\n			return true;\r\n		}\r\n		*/\r\n		\r\n		return false;\r\n	}\r\n	\r\n}\r\n', '2016-04-14 19:55:26', '2016-04-29 18:29:33'), ('12', '0', 'DemoHandlerBImpl', '略', 'package com.xxl.groovy.example.service.impl;\r\n\r\nimport java.util.HashSet;\r\nimport java.util.Map;\r\nimport java.util.Set;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * 场景B：托管 “静态方法”，\r\n * 优点：...； + 组件共享；\r\n * @author xuxueli 2016-4-14 16:07:03\r\n */\r\npublic class DemoHandlerBImpl implements GlueHandler {\r\n	private static final String SHOPID = \"shopid\";\r\n	\r\n	private static Set<Integer> blackShops = new HashSet<Integer>();\r\n	static {\r\n		blackShops.add(15826714);\r\n		blackShops.add(15826715);\r\n		blackShops.add(15826716);\r\n		blackShops.add(15826717);\r\n		blackShops.add(15826718);\r\n		blackShops.add(15826719);\r\n	}\r\n	\r\n	/**\r\n	 * 商户黑名单判断\r\n	 */\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n		int shopid = 0;\r\n		if (params!=null && params.get(SHOPID)!=null) {\r\n			shopid = (Integer) params.get(SHOPID);\r\n		}\r\n		if (shopid > 0 && blackShops.contains(shopid)) {\r\n			return true;\r\n		}\r\n		return false;\r\n	}\r\n	\r\n}\r\n', '2016-04-14 19:55:54', '2016-04-29 18:29:33'), ('13', '0', 'DemoHandlerAImpl', '略', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Arrays;\nimport java.util.HashMap;\nimport java.util.HashSet;\nimport java.util.List;\nimport java.util.Map;\nimport java.util.Set;\n\nimport com.xxl.glue.core.handler.GlueHandler;\n\n/**\n * 场景A：托管 “配置信息” ，尤其适用于数据结构比较复杂的配置项\n * 优点：在线编辑；推送更新；+ 直观；\n * @author xuxueli 2016-4-14 15:36:37\n */\npublic class DemoHandlerAImpl implements GlueHandler {\n\n	@Override\n	public Object handle(Map<String, Object> params) {\n		\n		// 【基础类型配置】\n		boolean ifOpen = true;															// 开关\n		int smsLimitCount = 7;															// 短信发送次数阀值\n		String brokerURL = \"failover:(tcp://127.0.0.1:61616,tcp://127.0.0.2:61616)\";	// 套接字配置\n		\n		// 【列表配置】\n		Set<Integer> blackShops = new HashSet<Integer>();								// 黑名单列表\n		blackShops.add(15826714);\n		blackShops.add(15826715);\n		blackShops.add(15826716);\n		blackShops.add(15826717);\n		blackShops.add(15826718);\n		blackShops.add(15826719);\n		\n		// 【KV配置】\n		Map<Integer, String> emailDispatch = new HashMap<Integer, String>();			// 不同BU标题文案配置\n		emailDispatch.put(555, \"淘宝\");\n		emailDispatch.put(666, \"天猫\");\n		emailDispatch.put(777, \"聚划算\");\n		\n		// 【复杂集合配置】\n		Map<Integer, List<Integer>> openCitys = new HashMap<Integer, List<Integer>>();	// 不同城市推荐商户配置\n		openCitys.put(11, Arrays.asList(15826714, 15826715));\n		openCitys.put(22, Arrays.asList(15826714, 15651231, 86451231));\n		openCitys.put(33, Arrays.asList(48612323, 15826715));\n		\n		return smsLimitCount;\n	}\n	\n}\n', '2016-04-14 19:56:15', '2017-05-28 23:44:07'), ('19', '5', 'demo_project.demoglue', '示例GLUE111', null, '2017-05-29 20:21:13', '2017-05-29 20:29:33'), ('20', '6', 'demo_project02.demoglue02', '示例GLUE222', null, '2017-05-29 20:25:34', '2017-05-29 20:29:26');
COMMIT;

-- ----------------------------
--  Table structure for `xxl_glue_project`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_project`;
CREATE TABLE `xxl_glue_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组',
  `appname` varchar(100) NOT NULL COMMENT '项目AppName',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `xxl_glue_project`
-- ----------------------------
BEGIN;
INSERT INTO `xxl_glue_project` VALUES ('5', 'demo_project', '示例项目'), ('6', 'demo_project02', '示例项目02');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
