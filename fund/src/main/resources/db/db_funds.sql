/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : db_funds

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-04-24 09:57:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for test_user
-- ----------------------------
DROP TABLE IF EXISTS `test_user`;
CREATE TABLE `test_user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '代理主键',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `email` varchar(64) DEFAULT NULL COMMENT '电子邮箱',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `sex` varchar(6) DEFAULT NULL COMMENT '性别[MALE FEMALE]',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uix_username` (`username`) USING BTREE,
  KEY `idx_email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=100004 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_fund
-- ----------------------------
DROP TABLE IF EXISTS `t_fund`;
CREATE TABLE `t_fund` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fund_name` varchar(32) NOT NULL COMMENT '基金名称',
  `fund_code` varchar(8) NOT NULL COMMENT '基金编码',
  `initial` varchar(16) DEFAULT NULL COMMENT '词首大写字母',
  `set_up_date` date NOT NULL COMMENT '成立日期',
  `fund_type_code` varchar(8) DEFAULT NULL COMMENT '基金类型编码',
  `fund_type_name` varchar(16) NOT NULL COMMENT '基金类型名称',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4950 DEFAULT CHARSET=utf8 COMMENT='基金表';

-- ----------------------------
-- Table structure for t_fund_trend
-- ----------------------------
DROP TABLE IF EXISTS `t_fund_trend`;
CREATE TABLE `t_fund_trend` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `fund_code` varchar(16) NOT NULL,
  `fund_name` varchar(32) NOT NULL,
  `statistics_date` date NOT NULL COMMENT '统计日期',
  `unit_net_worth` varchar(16) NOT NULL COMMENT '单位净值',
  `total_net_worth` varchar(16) NOT NULL COMMENT '累计净值',
  `daily_rate` varchar(16) DEFAULT NULL COMMENT '日增长率',
  `last_week_rate` varchar(16) DEFAULT NULL,
  `last_month_rate` varchar(16) DEFAULT NULL,
  `last_3_month_rate` varchar(16) DEFAULT NULL,
  `last_6_month_rate` varchar(16) DEFAULT NULL,
  `last_year_rate` varchar(16) DEFAULT NULL,
  `last_2_year_rate` varchar(16) DEFAULT NULL,
  `last_3_year_rate` varchar(16) DEFAULT NULL,
  `this_year_rate` varchar(16) DEFAULT NULL,
  `since_inception_rate` varchar(16) DEFAULT NULL,
  `service_charge` varchar(16) DEFAULT NULL,
  `ext17` varchar(32) DEFAULT NULL,
  `ext18` varchar(32) DEFAULT NULL,
  `ext19` varchar(32) DEFAULT NULL,
  `ext21` varchar(32) DEFAULT NULL,
  `ext22` varchar(32) DEFAULT NULL,
  `ext23` varchar(32) DEFAULT NULL,
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uix_code_date` (`statistics_date`,`fund_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=125129 DEFAULT CHARSET=utf8 COMMENT='基金收益趋势';

-- ----------------------------
-- Table structure for t_fund_type
-- ----------------------------
DROP TABLE IF EXISTS `t_fund_type`;
CREATE TABLE `t_fund_type` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `type_code` varchar(16) NOT NULL COMMENT '类型编码',
  `type_name` varchar(32) NOT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='基金类型';
