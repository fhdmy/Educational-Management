/*
 Navicat MariaDB Data Transfer

 Source Server         : demo
 Source Server Type    : MariaDB
 Source Server Version : 100406
 Source Host           : localhost:3306
 Source Schema         : xks

 Target Server Type    : MariaDB
 Target Server Version : 100406
 File Encoding         : 65001

 Date: 20/03/2020 10:39:23
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for choose_course
-- ----------------------------
DROP TABLE IF EXISTS `choose_course`;
CREATE TABLE `choose_course` (
  `sid` char(8) NOT NULL COMMENT '选课学生学号',
  `cid` char(8) NOT NULL COMMENT '课程号',
  `tid` char(8) NOT NULL COMMENT '教师号',
  `time` char(20) NOT NULL COMMENT '上课时间',
  PRIMARY KEY (`sid`,`cid`,`tid`,`time`) USING BTREE,
  KEY `courseid` (`cid`),
  KEY `teacherid` (`tid`),
  KEY `time` (`time`),
  CONSTRAINT `courseid` FOREIGN KEY (`cid`) REFERENCES `offer_course` (`cid`),
  CONSTRAINT `studentid` FOREIGN KEY (`sid`) REFERENCES `student` (`sid`),
  CONSTRAINT `teacherid` FOREIGN KEY (`tid`) REFERENCES `offer_course` (`tid`),
  CONSTRAINT `time` FOREIGN KEY (`time`) REFERENCES `offer_course` (`time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选课表';

-- ----------------------------
-- Records of choose_course
-- ----------------------------
BEGIN;
INSERT INTO `choose_course` VALUES ('17120198', '00001001', '00001010', '五3-4');
INSERT INTO `choose_course` VALUES ('17120198', '00001100', '00001010', '五7-8');
COMMIT;

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `cid` char(8) NOT NULL COMMENT '课程号',
  `cname` char(20) DEFAULT NULL COMMENT '课程名',
  `score` smallint(1) DEFAULT NULL COMMENT '学分',
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

-- ----------------------------
-- Records of course
-- ----------------------------
BEGIN;
INSERT INTO `course` VALUES ('00001001', '数据结构', 4);
INSERT INTO `course` VALUES ('00001011', '机器学习', 4);
INSERT INTO `course` VALUES ('00001100', '数据库', 4);
INSERT INTO `course` VALUES ('00001208', '操作系统', 5);
INSERT INTO `course` VALUES ('02000330', '计算机导论', 2);
INSERT INTO `course` VALUES ('02001001', '计算机前沿技术', 2);
INSERT INTO `course` VALUES ('02118001', '计算机组成原理', 4);
INSERT INTO `course` VALUES ('02300113', '初等数论', 3);
INSERT INTO `course` VALUES ('08001311', '网络安全', 3);
COMMIT;

-- ----------------------------
-- Table structure for offer_course
-- ----------------------------
DROP TABLE IF EXISTS `offer_course`;
CREATE TABLE `offer_course` (
  `tid` char(8) NOT NULL COMMENT '开课教师号',
  `cid` char(8) NOT NULL COMMENT '课程号',
  `maxnum` smallint(3) DEFAULT NULL COMMENT '最大人数',
  `place` char(20) DEFAULT NULL COMMENT '开课地点',
  `time` char(20) NOT NULL COMMENT '开课时间',
  `chosennum` smallint(3) DEFAULT NULL COMMENT '已选人数',
  PRIMARY KEY (`tid`,`cid`,`time`) USING BTREE,
  KEY `time` (`time`),
  KEY `tid` (`tid`),
  KEY `cid` (`cid`),
  CONSTRAINT `cid` FOREIGN KEY (`cid`) REFERENCES `course` (`cid`),
  CONSTRAINT `tid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='开课表';

-- ----------------------------
-- Records of offer_course
-- ----------------------------
BEGIN;
INSERT INTO `offer_course` VALUES ('00001010', '00001001', 150, '宝山', '五3-4', 1);
INSERT INTO `offer_course` VALUES ('00001010', '00001100', 150, '宝山', '三3-4', 0);
INSERT INTO `offer_course` VALUES ('00001010', '00001100', 150, '宝山', '五7-8', 1);
INSERT INTO `offer_course` VALUES ('00001011', '02001001', 30, '宝山', '四6-7', 0);
INSERT INTO `offer_course` VALUES ('00001011', '02118001', 50, '宝山', '四1-2', 0);
INSERT INTO `offer_course` VALUES ('00001111', '00001001', 100, '宝山', '一1-3', 0);
INSERT INTO `offer_course` VALUES ('00001111', '02118001', 80, '宝山', '一11-13', 0);
INSERT INTO `offer_course` VALUES ('00001111', '02300113', 60, '宝山', '三10-13', 0);
INSERT INTO `offer_course` VALUES ('00010081', '02300113', 100, '宝山', '一10-13', 0);
INSERT INTO `offer_course` VALUES ('00010081', '08001311', 40, '宝山', '二7-10', 0);
INSERT INTO `offer_course` VALUES ('10000202', '00001208', 50, '宝山', '四8-10', 0);
INSERT INTO `offer_course` VALUES ('10000202', '02000330', 40, '宝山', '五3-4', 0);
INSERT INTO `offer_course` VALUES ('10182000', '00001011', 50, '宝山', '四10-13', 0);
INSERT INTO `offer_course` VALUES ('10182000', '00001208', 30, '宝山', '一1-2', 0);
INSERT INTO `offer_course` VALUES ('10182000', '02118001', 60, '宝山', '三1-4', 0);
COMMIT;

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `sid` char(8) NOT NULL COMMENT '学生id(学号)',
  `sname` char(12) NOT NULL COMMENT '学生姓名',
  `spassword` char(20) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生表';

-- ----------------------------
-- Records of student
-- ----------------------------
BEGIN;
INSERT INTO `student` VALUES ('17120198', '姜辰昊', '123456');
INSERT INTO `student` VALUES ('17120199', '胡逸冲', '1357911');
INSERT INTO `student` VALUES ('17120200', '刘子航', '246810');
COMMIT;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `tid` char(8) NOT NULL COMMENT '教师号',
  `tname` char(12) DEFAULT NULL COMMENT '教师姓名',
  `tpassword` char(20) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教师表';

-- ----------------------------
-- Records of teacher
-- ----------------------------
BEGIN;
INSERT INTO `teacher` VALUES ('00001010', '郑宇', '123456');
INSERT INTO `teacher` VALUES ('00001011', '王鹏', '123456');
INSERT INTO `teacher` VALUES ('00001111', '沈俊', '123456');
INSERT INTO `teacher` VALUES ('00010081', '沈云付', '123456');
INSERT INTO `teacher` VALUES ('10000202', '岳晓东', '123456');
INSERT INTO `teacher` VALUES ('10182000', '张健', '123456');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
