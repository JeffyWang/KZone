/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : kzone

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2014-06-19 15:52:23
*/


-- ----------------------------
-- Table structure for `k_area`
-- ----------------------------
DROP TABLE IF EXISTS `k_area`;
CREATE TABLE `k_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_id` varchar(16) NOT NULL DEFAULT '',
  `area_name` varchar(16) NOT NULL DEFAULT '',
  `reference` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_area
-- ----------------------------

-- ----------------------------
-- Table structure for `k_city`
-- ----------------------------
DROP TABLE IF EXISTS `k_city`;
CREATE TABLE `k_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` varchar(16) NOT NULL DEFAULT '',
  `city_name` varchar(16) NOT NULL DEFAULT '',
  `reference` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=346 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_city
-- ----------------------------

-- ----------------------------
-- Table structure for `k_comment`
-- ----------------------------
DROP TABLE IF EXISTS `k_comment`;
CREATE TABLE `k_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ktv_id` int(11) DEFAULT '0',
  `comment` varchar(20480) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `environment_score` float DEFAULT '0',
  `service_score` float DEFAULT '0',
  `sound_effects_score` float DEFAULT '0',
  `user_id` int(11) DEFAULT '0',
  `score` float DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_comment
-- ----------------------------

-- ----------------------------
-- Table structure for `k_district`
-- ----------------------------
DROP TABLE IF EXISTS `k_district`;
CREATE TABLE `k_district` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `district` varchar(32) NOT NULL DEFAULT '',
  `district_code` varchar(32) NOT NULL DEFAULT '',
  `municipality` varchar(32) NOT NULL DEFAULT '',
  `municipality_code` varchar(8) NOT NULL DEFAULT '',
  `province` varchar(16) NOT NULL DEFAULT '',
  `province_code` varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_district
-- ----------------------------

-- ----------------------------
-- Table structure for `k_game`
-- ----------------------------
DROP TABLE IF EXISTS `k_game`;
CREATE TABLE `k_game` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `game` longtext,
  `introduction` text,
  `link` varchar(256) DEFAULT '',
  `name` varchar(128) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_game
-- ----------------------------

-- ----------------------------
-- Table structure for `k_information`
-- ----------------------------
DROP TABLE IF EXISTS `k_information`;
CREATE TABLE `k_information` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article` longtext,
  `create_time` datetime DEFAULT NULL,
  `introduction` text,
  `link` varchar(256) DEFAULT '',
  `title` varchar(128) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_information
-- ----------------------------

-- ----------------------------
-- Table structure for `k_ktv`
-- ----------------------------
DROP TABLE IF EXISTS `k_ktv`;
CREATE TABLE `k_ktv` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(256) DEFAULT '',
  `average_price` int(11) DEFAULT '0',
  `create_time` datetime DEFAULT NULL,
  `district_id` varchar(128) DEFAULT '0',
  `geographic_information` varchar(32) DEFAULT '',
  `introduction` varchar(10240) DEFAULT '',
  `name` varchar(128) NOT NULL DEFAULT '',
  `phone_number` varchar(16) DEFAULT '',
  `pictures` varchar(4096) DEFAULT '',
  `score` float DEFAULT '0',
  `update_time` datetime DEFAULT NULL,
  `price` varchar(32) DEFAULT '0.0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=548 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_ktv
-- ----------------------------

-- ----------------------------
-- Table structure for `k_province`
-- ----------------------------
DROP TABLE IF EXISTS `k_province`;
CREATE TABLE `k_province` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `province_id` varchar(16) NOT NULL DEFAULT '',
  `province_name` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_province
-- ----------------------------

-- ----------------------------
-- Table structure for `k_statistics`
-- ----------------------------
DROP TABLE IF EXISTS `k_statistics`;
CREATE TABLE `k_statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT '0',
  `app_name` varchar(128) NOT NULL DEFAULT '',
  `app_version` varchar(128) NOT NULL DEFAULT '',
  `os_type` varchar(128) NOT NULL DEFAULT '',
  `os_version` varchar(128) NOT NULL DEFAULT '',
  `token` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_statistics
-- ----------------------------

-- ----------------------------
-- Table structure for `k_user`
-- ----------------------------
DROP TABLE IF EXISTS `k_user`;
CREATE TABLE `k_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL,
  `favorite` varchar(128) DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `user_name` varchar(16) NOT NULL DEFAULT '',
  `uuid` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of k_user
-- ----------------------------
