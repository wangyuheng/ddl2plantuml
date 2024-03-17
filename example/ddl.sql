# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: localhost (MySQL 5.5.14-log)
# Database: my_db
# Generation Time: 2015-08-21 17:11:51 +0000
# ************************************************************

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */

-- table 1
CREATE TABLE `table_1` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT 'column_1',
  `prod_name` varchar(20) NOT NULL COMMENT 'column_2',
  `prod_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT 'column_3 0:活期 1:定期',
  `start_time` time NOT NULL COMMENT '停止交易开始时间',
  `end_time` time NOT NULL COMMENT '停止交易结束时间',
  `online_type` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '0:上线 1:未上线',
  `prod_info` varchar(2000) NOT NULL DEFAULT '' COMMENT '产品介绍',
  `over_limit` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '超额限制 0:限制 1:不限制',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='This is table 1;';

create table `table_2` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户id',
  `user_name` varchar(50) NOT NULL COMMENT '用户名称',
  `prod_id` bigint(20) unsigned NOT NULL COMMENT '产品id',
  `interest_date` date DEFAULT NULL COMMENT '计息日期',
  `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_user_id_prod_id` (`user_id`,`prod_id`) USING BTREE
) COMMENT='This is table 2'