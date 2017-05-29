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

 Date: 05/29/2017 00:30:18 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `xxl_glue_code_info`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_code_info`;
CREATE TABLE `xxl_glue_code_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `biz_id` int(11) NOT NULL COMMENT '业务线ID',
  `name` varchar(128) NOT NULL,
  `about` varchar(128) NOT NULL,
  `source` text,
  `remark` varchar(128) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_name` (`name`) USING BTREE,
  KEY `u_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xxl_glue_code_log`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_code_log`;
CREATE TABLE `xxl_glue_code_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `about` varchar(128) NOT NULL,
  `source` text,
  `remark` varchar(128) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `i_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xxl_glue_group`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_group`;
CREATE TABLE `xxl_glue_group` (
  `id` int(11) NOT NULL COMMENT '分组',
  `name` varchar(100) NOT NULL COMMENT '业务线名称',
  `order` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `xxl_glue_user`
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_user`;
CREATE TABLE `xxl_glue_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `role` tinyint(4) NOT NULL COMMENT '角色：0-普通用户、1-管理员',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;
