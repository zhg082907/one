SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '客商主键',
  `customer` varchar(100) DEFAULT NULL COMMENT '客商',
  `code` varchar(20) DEFAULT NULL COMMENT '客商编码',
  `vid` int(11) DEFAULT NULL COMMENT '版本主键',
  `flag` int(11) DEFAULT NULL COMMENT '新增标记 0导入 1新增 2变更',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(40) DEFAULT NULL COMMENT '创建人主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=237 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('214', '123', 'A01', '91', '0', '2018-05-11 15:34:27', '1');
INSERT INTO `customer` VALUES ('215', null, 'A01', '92', '0', '2018-05-11 15:35:25', '1');
INSERT INTO `customer` VALUES ('216', null, 'A01', '93', '0', '2018-05-11 15:35:52', '1');
INSERT INTO `customer` VALUES ('217', null, 'A01', '94', '0', '2018-05-11 15:36:46', '1');
INSERT INTO `customer` VALUES ('218', '333333', 'B01', '94', '0', '2018-05-11 15:36:46', '1');
INSERT INTO `customer` VALUES ('219', '4444', 'C01', '94', '0', '2018-05-11 15:36:46', '1');
INSERT INTO `customer` VALUES ('220', null, 'A01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('221', null, 'B01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('222', null, 'C01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('223', null, 'D01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('224', null, 'E01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('225', null, 'F01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('226', null, 'G01', '97', '0', '2018-05-11 16:09:20', '1');
INSERT INTO `customer` VALUES ('227', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', 'A01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('228', '测试1', 'B01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('229', '测试', 'C01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('230', '西宁粮食局直属单位', 'D01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('231', '青海某科室机构', 'E01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('232', '客商用户信息', 'F01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('233', '测试客商用户有点长的思念', 'G01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('234', '客商用户信息1111', 'H01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('235', '客商海南的信息', 'I01', '98', '0', '2018-05-11 16:10:21', '1');
INSERT INTO `customer` VALUES ('236', '客商粮食局化身', 'J01', '98', '0', '2018-05-11 16:10:21', '1');

-- ----------------------------
-- Table structure for gok
-- ----------------------------
DROP TABLE IF EXISTS `gok`;
CREATE TABLE `gok` (
  `gok_name` varchar(40) NOT NULL COMMENT '粮油品种名称',
  PRIMARY KEY (`gok_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gok
-- ----------------------------
INSERT INTO `gok` VALUES ('小麦粉');
INSERT INTO `gok` VALUES ('粳米');
INSERT INTO `gok` VALUES ('花生');

-- ----------------------------
-- Table structure for price
-- ----------------------------
DROP TABLE IF EXISTS `price`;
CREATE TABLE `price` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '价格主键',
  `price` double DEFAULT NULL COMMENT '价格',
  `code` varchar(20) DEFAULT NULL COMMENT '价格编码',
  `vid` int(11) DEFAULT NULL COMMENT '版本主键',
  `flag` int(11) DEFAULT NULL COMMENT '新增标记 0导入 1新增 2变更',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(40) DEFAULT NULL COMMENT '创建人主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1742 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of price
-- ----------------------------
INSERT INTO `price` VALUES ('1728', '9.99999', 'A01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1729', '8.88888', 'B01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1730', '7.77777', 'C01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1731', '0', 'D01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1732', '0.99999', 'E01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1733', '1222', 'F01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1734', '222', 'G01', '95', '0', '2018-05-11 15:39:57', '1');
INSERT INTO `price` VALUES ('1735', '9.99999', 'A01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1736', '8.88888', 'B01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1737', '7.77777', 'C01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1738', '0', 'D01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1739', '0.99999', 'E01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1740', '1222', 'F01', '96', '0', '2018-05-11 15:49:35', '1');
INSERT INTO `price` VALUES ('1741', '222', 'G01', '96', '0', '2018-05-11 15:49:35', '1');

-- ----------------------------
-- Table structure for report
-- ----------------------------
DROP TABLE IF EXISTS `report`;
CREATE TABLE `report` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 自增',
  `out_way` varchar(20) DEFAULT NULL COMMENT '出库方式',
  `out_notice_num` varchar(40) DEFAULT NULL COMMENT '出库通知单号',
  `billing_date` datetime DEFAULT NULL COMMENT '开票日期',
  `out_date` datetime DEFAULT NULL COMMENT '出库日期',
  `customer` varchar(100) DEFAULT NULL COMMENT '客商',
  `grain_oil_kind` varchar(20) DEFAULT NULL COMMENT '粮油品种',
  `item_name` varchar(100) DEFAULT NULL COMMENT '商品名称',
  `grade` varchar(20) DEFAULT NULL COMMENT '等级',
  `spec` int(11) DEFAULT NULL COMMENT '规格',
  `num` int(11) DEFAULT NULL COMMENT '件数',
  `wh_num` varchar(20) DEFAULT NULL COMMENT '出库仓号',
  `location` varchar(20) DEFAULT NULL COMMENT '库位',
  `unit_price` double DEFAULT NULL COMMENT '单价',
  `total_price` double DEFAULT NULL COMMENT '总价',
  `create_id` varchar(40) DEFAULT NULL COMMENT '创建人主键',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report
-- ----------------------------
INSERT INTO `report` VALUES ('42', '零散出库', 'CK201804180111', '2018-05-11 16:14:40', '2018-05-11 16:22:53', '测试', '花生', '花生米', '一级', '10', '1', '4号库', '4号库3号货位', '0', '0', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('43', '零散出库', 'CK201804180122', '2018-05-11 16:14:40', '2018-05-11 16:22:42', '西宁粮食局直属单位', '粳米', '润丰', '一级', '25', '1', '2号库', '5号垛润丰军裕', '9.99999', '249.99975', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('44', '零散出库', 'CK201804180111', '2018-05-11 16:14:40', '2018-05-10 20:16:28', '测试', '花生', '花生米', '一级', '10', '1', '4号库', '4号库3号货位', '0', '0', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('45', '零散出库', 'CK201804180110', '2018-05-11 16:14:40', '2018-05-10 19:48:56', '青海某科室机构', '粳米', '润丰', '一级', '25', '1', '2号库', '测试货位B', '8.88888', '222.222', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('46', '汽车出库', 'CK201804180110', '2018-05-11 16:14:40', '2018-05-10 19:48:42', '测试', '粳米', '润丰', '一级', '25', '1', '2号库', '测试货位B', '7.77777', '194.44425', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('47', '汽车出库', 'CK201804180109', '2018-05-11 16:14:40', '2018-05-10 19:45:18', '测试1', '粳米', '润丰', '一级', '25', '3', '2号库', '5号垛润丰军裕', '0.99999', '74.99925', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('48', '汽车出库', 'CK201804180109', '2018-05-11 16:14:40', '2018-05-10 19:03:57', '测试1', '粳米', '润丰', '一级', '25', '1', '2号库', '5号垛润丰军裕', '0.99999', '24.99975', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('49', '零散出库', 'CK201804180103', '2018-05-11 16:14:40', '2018-05-10 15:14:22', '客商用户信息1111', '粳米', '润丰', '一级', '25', '1', '2号库', '5号垛润丰军裕', '222', '5550', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('50', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-09 15:33:53', '测试', '小麦粉', '金龙鱼面粉', '二级', '25', '5', '成品粮一仓房', '小麦成品粮货位', '0', '0', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('51', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-09 15:33:53', '西宁粮食局直属单位', '花生', '三级压榨花生油', '三级', '20', '2', '成品粮一仓房', '花生成品粮货位', '1222', '48880', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('52', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-09 15:33:53', '客商海南的信息', '小麦粉', '五得利面粉', '一级', '5', '15', '成品粮一仓房', '小麦成品粮货位', '0.99999', '74.99925', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('53', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:25:53', '测试1', '小麦粉', '金龙鱼面粉', '二级', '25', '5', '成品粮一仓房', '小麦成品粮货位', '0.99999', '124.99875', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('54', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:25:53', '测试客商用户有点长的思念', '花生', '三级压榨花生油', '三级', '20', '3', '成品粮一仓房', '花生成品粮货位', '9.99999', '599.9994', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('55', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:17:42', '客商用户信息', '花生', '三级压榨花生油', '三级', '20', '5', '成品粮一仓房', '花生成品粮货位', '8.88888', '888.888', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('56', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:16:35', '测试1', '小麦粉', '五得利面粉', '一级', '5', '5', '成品粮一仓房', '小麦成品粮货位', '0', '0', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('57', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:00:32', '测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试', '小麦粉', '金龙鱼面粉', '二级', '25', '10', '成品粮一仓房', '小麦成品粮货位', '7.77777', '1944.4425', '1', '2018-05-11 16:30:15');
INSERT INTO `report` VALUES ('58', '汽车出库', 'CK201804180095', '2018-05-11 16:14:40', '2018-05-08 19:00:32', '客商用户信息1111', '花生', '三级压榨花生油', '三级', '20', '10', '成品粮一仓房', '花生成品粮货位', '9.99999', '1999.998', '1', '2018-05-11 16:30:15');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户自增主键',
  `user_name` varchar(40) DEFAULT NULL COMMENT '姓名',
  `login_name` varchar(40) DEFAULT NULL COMMENT '登录名',
  `pwd` varchar(40) DEFAULT NULL COMMENT '密码',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '管理员', 'admin', '111111', '2018-04-25 11:25:27');
INSERT INTO `user` VALUES ('6', '文玉杰', 'wenyujie', '111111', '2018-04-28 16:34:28');
INSERT INTO `user` VALUES ('7', '苏文枫', 'suwenfeng', '111111', '2018-05-07 11:07:18');
INSERT INTO `user` VALUES ('8', '郑洪刚', 'zhg', '111111', '2018-05-07 16:25:36');
INSERT INTO `user` VALUES ('10', '111', '1111', '@@@@@@@^&*:\"?><', '2018-05-09 19:23:55');
INSERT INTO `user` VALUES ('11', '2222222', '22222', '2222222', '2018-05-09 19:26:19');
INSERT INTO `user` VALUES ('12', '223333', '233333', '111111', '2018-05-10 10:17:28');
INSERT INTO `user` VALUES ('13', '2222', '222', '2222222', '2018-05-10 10:17:45');
INSERT INTO `user` VALUES ('14', '</1%11>', '1111111110', '1111111110', '2018-05-10 10:25:44');
INSERT INTO `user` VALUES ('17', 'wyx', 'wyx', '111111', '2018-05-10 11:14:51');
INSERT INTO `user` VALUES ('18', '111111', '111111', '111111', '2018-05-10 14:01:43');
INSERT INTO `user` VALUES ('19', '111111111111', '111111111111', '111111111111', '2018-05-10 14:01:55');
INSERT INTO `user` VALUES ('20', '111111111111111111', '11111111111111', '11111111111111', '2018-05-11 09:33:01');
INSERT INTO `user` VALUES ('21', 'test', 'test1', '111111', '2018-05-11 10:30:49');
INSERT INTO `user` VALUES ('22', '测试测试测试测试测试测试测试测试测试测试', '11111111111111111111', '11111111111111111111', '2018-05-11 10:41:50');

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '版本主键',
  `type` int(11) DEFAULT NULL COMMENT '版本类型 0价格 1客商',
  `code` varchar(20) DEFAULT NULL COMMENT '版本编码',
  `status` int(11) DEFAULT NULL COMMENT '版本状态 0正常 1失效',
  `begin_date` datetime DEFAULT NULL COMMENT '生效日期',
  `end_date` datetime DEFAULT NULL COMMENT '失效日期',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `create_id` varchar(40) DEFAULT NULL COMMENT '创建人主键',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('91', '1', 'V1.0', '1', '2018-05-11 15:34:27', '2018-05-11 15:35:25', '2018-05-11 15:34:27', '1');
INSERT INTO `version` VALUES ('92', '1', 'V1.1', '1', '2018-05-11 15:35:26', '2018-05-11 15:35:52', '2018-05-11 15:35:26', '1');
INSERT INTO `version` VALUES ('93', '1', 'V1.2', '1', '2018-05-11 15:35:53', '2018-05-11 15:36:46', '2018-05-11 15:35:53', '1');
INSERT INTO `version` VALUES ('94', '1', 'V1.3', '1', '2018-05-11 15:36:47', '2018-05-11 16:09:19', '2018-05-11 15:36:47', '1');
INSERT INTO `version` VALUES ('95', '0', 'V1.0', '1', '2018-05-11 15:39:57', '2018-05-11 15:49:35', '2018-05-11 15:39:57', '1');
INSERT INTO `version` VALUES ('96', '0', 'V1.1', '0', '2018-05-11 15:49:36', null, '2018-05-11 15:49:36', '1');
INSERT INTO `version` VALUES ('97', '1', 'V1.4', '1', '2018-05-11 16:09:20', '2018-05-11 16:10:21', '2018-05-11 16:09:20', '1');
INSERT INTO `version` VALUES ('98', '1', 'V1.5', '0', '2018-05-11 16:10:22', null, '2018-05-11 16:10:22', '1');
