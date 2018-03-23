/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50721
Source Host           : localhost:3306
Source Database       : food

Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001

Date: 2018-03-23 17:43:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `order_id` varchar(32) NOT NULL,
  `buyer_name` varchar(64) NOT NULL COMMENT '买家名称',
  `buyer_phone` varchar(11) NOT NULL COMMENT '买家手机号',
  `buyer_address` varchar(256) NOT NULL COMMENT '买家地址',
  `buyer_openid` varchar(32) NOT NULL COMMENT '买家微信id',
  `order_price` decimal(8,2) NOT NULL COMMENT '订单金额',
  `order_status` int(11) NOT NULL DEFAULT '0' COMMENT '订单状态 默认0 最新下单',
  `pay_status` int(11) NOT NULL DEFAULT '0' COMMENT '支付状态， 默认0 未支付',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`order_id`),
  KEY `idx_openid` (`buyer_openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

-- ----------------------------
-- Records of order
-- ----------------------------

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `detail_id` varchar(32) NOT NULL,
  `product_id` varchar(32) NOT NULL COMMENT '商品id',
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '商品价格',
  `product_amount` int(11) NOT NULL COMMENT '商品数量',
  `product_img` varchar(512) NOT NULL COMMENT '商品图片',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`detail_id`),
  KEY `idx_product` (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单详情表';

-- ----------------------------
-- Records of order_detail
-- ----------------------------

-- ----------------------------
-- Table structure for product_category
-- ----------------------------
DROP TABLE IF EXISTS `product_category`;
CREATE TABLE `product_category` (
  `category_id` varchar(32) NOT NULL,
  `category_name` varchar(64) NOT NULL COMMENT '类目名称',
  `category_type` varchar(32) NOT NULL COMMENT '类目编号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uqe_category_type` (`category_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='类目表';

-- ----------------------------
-- Records of product_category
-- ----------------------------
INSERT INTO `product_category` VALUES ('1122211', '粥类', '112', '2018-03-22 17:05:12', '2018-03-22 17:05:12');
INSERT INTO `product_category` VALUES ('1123212', '蔬菜类', '113', '2018-03-22 17:05:31', '2018-03-22 17:05:31');

-- ----------------------------
-- Table structure for product_info
-- ----------------------------
DROP TABLE IF EXISTS `product_info`;
CREATE TABLE `product_info` (
  `product_id` varchar(32) NOT NULL,
  `product_name` varchar(64) NOT NULL COMMENT '商品名称',
  `product_price` decimal(8,2) NOT NULL COMMENT '单价',
  `product_stock` int(11) NOT NULL COMMENT '库存',
  `product_desc` varchar(256) DEFAULT NULL COMMENT '描述',
  `product_img` varchar(512) DEFAULT NULL COMMENT '图片',
  `product_status` int(11) NOT NULL COMMENT '商品状态 0-正常 1-下架',
  `category_type` varchar(32) DEFAULT NULL COMMENT '类目',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of product_info
-- ----------------------------
INSERT INTO `product_info` VALUES ('1112211', '麻辣蔬菜堡', '25.50', '50', '麻辣蔬菜堡非常热销，有微辣，中辣，麻辣', 'http://192.168.101.151/s.png', '0', '113', '2018-03-22 17:06:43', '2018-03-23 14:53:50');
INSERT INTO `product_info` VALUES ('1343421', '蔬菜拼盘', '15.00', '120', '蔬菜拼盘有各种蔬菜，胡萝卜，大白菜，青菜等等', 'http://192.168.101.151/ss.png', '0', '113', '2018-03-22 17:08:39', '2018-03-23 14:53:54');
INSERT INTO `product_info` VALUES ('9928232', '鱼香八宝粥', '8.50', '78', '鱼香八宝粥很不错啊', 'http://192.168.101.151/z.png', '0', '112', '2018-03-22 17:09:41', '2018-03-23 14:53:58');
