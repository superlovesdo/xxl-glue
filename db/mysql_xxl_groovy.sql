/*
Navicat MySQL Data Transfer

Source Server         : meme-127.0.0.1
Source Server Version : 50544
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-01-19 15:25:57
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xxl_groovy_code_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_groovy_code_info`;
CREATE TABLE `xxl_groovy_code_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `source` text,
  `remark` varchar(255) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_groovy_code_info
-- ----------------------------
INSERT INTO `xxl_groovy_code_info` VALUES ('8', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"009\";\n	}\n	\n}\n', '返回值修改为009', '2016-01-02 16:28:43', '2016-01-19 15:23:57');
INSERT INTO `xxl_groovy_code_info` VALUES ('9', 'load_timestamp_util', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    // 支持注入您的自定义服务\n    //@Resource \n	//private DemoServiceLoader demoService;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n		return System.currentTimeMillis();\n	}\n	\n}\n', '内部代码重构优化', '2016-01-08 21:52:24', '2016-01-08 21:55:39');

-- ----------------------------
-- Table structure for xxl_groovy_code_log
-- ----------------------------
DROP TABLE IF EXISTS `xxl_groovy_code_log`;
CREATE TABLE `xxl_groovy_code_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `source` text,
  `remark` varchar(255) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_groovy_code_log
-- ----------------------------
INSERT INTO `xxl_groovy_code_log` VALUES ('16', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return 111;\n	}\n	\n}\n', '返回值修改为111', '2016-01-19 15:12:57', '2016-01-19 15:12:57');
INSERT INTO `xxl_groovy_code_log` VALUES ('17', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return 222;\n	}\n	\n}\n', '返回值修改为222', '2016-01-19 15:20:01', '2016-01-19 15:20:01');
INSERT INTO `xxl_groovy_code_log` VALUES ('18', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"001\";\n	}\n	\n}\n', '返回值修改为001', '2016-01-19 15:20:10', '2016-01-19 15:20:10');
INSERT INTO `xxl_groovy_code_log` VALUES ('19', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"002\";\n	}\n	\n}\n', '返回值修改为002', '2016-01-19 15:20:26', '2016-01-19 15:20:26');
INSERT INTO `xxl_groovy_code_log` VALUES ('20', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"003\";\n	}\n	\n}\n', '返回值修改为003', '2016-01-19 15:21:15', '2016-01-19 15:21:15');
INSERT INTO `xxl_groovy_code_log` VALUES ('21', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"004\";\n	}\n	\n}\n', '返回值修改为004', '2016-01-19 15:21:29', '2016-01-19 15:21:29');
INSERT INTO `xxl_groovy_code_log` VALUES ('22', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"005\";\n	}\n	\n}\n', '返回值修改为005', '2016-01-19 15:22:15', '2016-01-19 15:22:15');
INSERT INTO `xxl_groovy_code_log` VALUES ('23', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"006\";\n	}\n	\n}\n', '返回值修改为006', '2016-01-19 15:23:06', '2016-01-19 15:23:06');
INSERT INTO `xxl_groovy_code_log` VALUES ('24', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"007\";\n	}\n	\n}\n', '返回值修改为007', '2016-01-19 15:23:43', '2016-01-19 15:23:43');
INSERT INTO `xxl_groovy_code_log` VALUES ('25', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.example.service.IDemoHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements IDemoHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"008\";\n	}\n	\n}\n', '返回值修改为008', '2016-01-19 15:23:57', '2016-01-19 15:23:57');
