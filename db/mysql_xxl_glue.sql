

CREATE TABLE `xxl_glue_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分组',
  `appname` varchar(100) NOT NULL COMMENT '项目AppName',
  `name` varchar(100) NOT NULL COMMENT '项目名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `xxl_glue_project` VALUES ('1', 'demo_project', '示例项目');



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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;

INSERT INTO `xxl_glue_glueinfo` VALUES ('1', '1', 'demo_project.demo_glue', '示例GLUE', 'package com.xxl.glue.example.gluehandler;\r\n\r\nimport java.util.HashSet;\r\nimport java.util.Set;\r\n\r\nimport com.xxl.glue.core.handler.GlueHandler;\r\n\r\n/**\r\n * GLUE示例\r\n *\r\n * @author xuxueli 2016-4-14 15:36:37\r\n */\r\npublic class DemoGlueHandler implements GlueHandler {\r\n\r\n	@Override\r\n	public Object handle(Map<String, Object> params) {\r\n\r\n		// 黑名单列表\r\n		Set<Integer> blackShops = new HashSet<Integer>();\r\n		blackShops.add(111);\r\n		blackShops.add(222);\r\n		blackShops.add(333);\r\n\r\n		return blackShops;\r\n	}\r\n\r\n}\r\n', '2017-05-29 22:55:57', '2017-05-29 22:55:57');



CREATE TABLE `xxl_glue_codelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `glue_id` int(11) NOT NULL COMMENT 'GLUE主键ID',
  `remark` varchar(128) NOT NULL,
  `source` text,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8;
