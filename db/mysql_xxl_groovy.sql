/*
Navicat MySQL Data Transfer

Source Server         : meme-127.0.0.1
Source Server Version : 50544
Source Host           : 127.0.0.1:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50544
File Encoding         : 65001

Date: 2016-01-23 20:05:16
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
INSERT INTO `xxl_groovy_code_info` VALUES ('8', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\n\nimport com.xxl.groovy.core.GlueLoader;\nimport com.xxl.groovy.example.dao.ICodeInfoDao;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Resource\n	private ICodeInfoDao codeInfoDao;\n    \n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    \n	    System.out.println(\"dbGlueLoader : \"+ dbGlueLoader);\n	    System.out.println(\"codeInfoDao : \"+ codeInfoDao);\n	    \n		return \"018\";\n	}\n	\n}\n', '返回值修改为018', '2016-01-02 16:28:43', '2016-01-23 19:44:43');
INSERT INTO `xxl_groovy_code_info` VALUES ('9', 'load_timestamp_util', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    // 支持注入您的自定义服务\n    //@Resource \n	//private DemoServiceLoader demoService;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n		return System.currentTimeMillis();\n	}\n	\n}\n', '修改基类信息', '2016-01-08 21:52:24', '2016-01-23 17:56:34');

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
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of xxl_groovy_code_log
-- ----------------------------
INSERT INTO `xxl_groovy_code_log` VALUES ('32', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\nimport com.xxl.groovy.core.GlueLoader;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    System.out.println(dbGlueLoader);\n		return \"013\";\n	}\n	\n}\n', '返回值修改为013', '2016-01-23 18:52:27', '2016-01-23 18:52:27');
INSERT INTO `xxl_groovy_code_log` VALUES ('33', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\n\nimport com.xxl.groovy.core.GlueLoader;\nimport com.xxl.groovy.example.dao.ICodeInfoDao;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Resource\n	private ICodeInfoDao codeInfoDao;\n    \n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    \n	    System.out.println(\"dbGlueLoader : \"+ dbGlueLoader);\n	    System.out.println(\"codeInfoDao : \"+ codeInfoDao);\n	    \n		return \"014\";\n	}\n	\n}\n', '返回值修改为014', '2016-01-23 19:13:04', '2016-01-23 19:13:04');
INSERT INTO `xxl_groovy_code_log` VALUES ('34', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\n\nimport com.xxl.groovy.core.GlueLoader;\nimport com.xxl.groovy.example.dao.ICodeInfoDao;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Autowired\n	private ICodeInfoDao codeInfoDao;\n    \n    @Autowired\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    \n	    System.out.println(\"dbGlueLoader : \"+ dbGlueLoader);\n	    System.out.println(\"codeInfoDao : \"+ codeInfoDao);\n	    \n		return \"015\";\n	}\n	\n}\n', '返回值修改为015', '2016-01-23 19:16:12', '2016-01-23 19:16:12');
INSERT INTO `xxl_groovy_code_log` VALUES ('35', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\n\nimport com.xxl.groovy.core.GlueLoader;\nimport com.xxl.groovy.example.dao.ICodeInfoDao;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Autowired\n	private ICodeInfoDao codeInfoDao;\n    \n    @Autowired\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    \n	    System.out.println(\"dbGlueLoader : \"+ dbGlueLoader);\n	    System.out.println(\"codeInfoDao : \"+ codeInfoDao);\n	    \n		return \"016\";\n	}\n	\n}\n', '返回值修改为016', '2016-01-23 19:40:10', '2016-01-23 19:40:10');
INSERT INTO `xxl_groovy_code_log` VALUES ('36', 'code_07', 'package com.xxl.groovy.example.service.impl;\n\nimport java.util.Map;\n\nimport com.xxl.groovy.core.service.GlueHandler;\n\nimport javax.annotation.Resource;\nimport org.springframework.beans.factory.annotation.Autowired;\n\nimport com.xxl.groovy.core.GlueLoader;\nimport com.xxl.groovy.example.dao.ICodeInfoDao;\n\npublic class DemoHandlerImpl implements GlueHandler {\n\n    @Autowired\n	private ICodeInfoDao codeInfoDao;\n    \n    @Resource\n	private GlueLoader dbGlueLoader;\n	\n	@Override\n	public Object handle(Map<String, Object> params) {\n	    \n	    System.out.println(\"dbGlueLoader : \"+ dbGlueLoader);\n	    System.out.println(\"codeInfoDao : \"+ codeInfoDao);\n	    \n		return \"017\";\n	}\n	\n}\n', '返回值修改为017', '2016-01-23 19:44:43', '2016-01-23 19:44:43');
