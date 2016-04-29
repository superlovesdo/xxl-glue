SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for xxl_glue_code_info
-- ----------------------------
DROP TABLE IF EXISTS `xxl_glue_code_info`;
CREATE TABLE `xxl_glue_code_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `about` varchar(128) NOT NULL,
  `source` text,
  `remark` varchar(128) NOT NULL,
  `add_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `i_name` (`name`) USING BTREE,
  KEY `u_name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for xxl_glue_code_log
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
