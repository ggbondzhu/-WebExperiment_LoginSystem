/*
 Navicat Premium Dump SQL

 Source Server         : mysql_docker
 Source Server Type    : MySQL
 Source Server Version : 90200 (9.2.0)
 Source Host           : localhost:3307
 Source Schema         : web_system

 Target Server Type    : MySQL
 Target Server Version : 90200 (9.2.0)
 File Encoding         : 65001

 Date: 08/03/2025 22:17:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for educational
-- ----------------------------
DROP TABLE IF EXISTS `educational`;
CREATE TABLE `educational` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `school` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '学校：',
  `major` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '专业：',
  `degree` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '学历：',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '2025.1.1-2025.1.1',
  PRIMARY KEY (`id`),
  KEY `project_user_user_id` (`user_id`),
  CONSTRAINT `educational_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of educational
-- ----------------------------
BEGIN;
INSERT INTO `educational` (`id`, `user_id`, `school`, `major`, `degree`, `name`) VALUES (8, 61, '学校：中国人民大学', '专业：电子信息', '学历：硕士', '2024.9.1-至今');
INSERT INTO `educational` (`id`, `user_id`, `school`, `major`, `degree`, `name`) VALUES (9, 61, '学校：清华大学', '专业：计算机科学与技术', '学历：本科', '2020.9.1-2024.6.1');
INSERT INTO `educational` (`id`, `user_id`, `school`, `major`, `degree`, `name`) VALUES (10, 61, '学校：人大附中', '专业：理科', '学历：高中', '2017.9.1-2020.6.1');
COMMIT;

-- ----------------------------
-- Table structure for personal_info
-- ----------------------------
DROP TABLE IF EXISTS `personal_info`;
CREATE TABLE `personal_info` (
  `user_id` int NOT NULL COMMENT '外键，用户唯一ID',
  `gender` bit(1) NOT NULL DEFAULT b'0' COMMENT '性别，0女，1男',
  `age` int NOT NULL DEFAULT '0' COMMENT '年龄',
  `school` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学校',
  `college` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学院',
  `major` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '专业',
  `sno` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '学号',
  `native_place` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '籍贯',
  `project_num` int NOT NULL DEFAULT '0' COMMENT '项目数',
  `fans_num` int NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `asset_num` int NOT NULL DEFAULT '0' COMMENT '资产数，单位万',
  `about` varchar(9999) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '该用户很懒，没有填写任何说明！提示：用户首次登录建议完善个人信息' COMMENT '个人简介',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '姓名',
  `techang` varchar(300) DEFAULT NULL COMMENT '特长',
  PRIMARY KEY (`user_id`) USING BTREE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of personal_info
-- ----------------------------
BEGIN;
INSERT INTO `personal_info` (`user_id`, `gender`, `age`, `school`, `college`, `major`, `sno`, `native_place`, `project_num`, `fans_num`, `asset_num`, `about`, `avatar_url`, `name`, `techang`) VALUES (61, b'1', 30, '中国人民大学', '信息学院', '电子信息', '123456999', '北京市', 9, 9999, 292929, '该用户很懒，没有填写任何说明！提示：用户首次登录建议完善个人信息', NULL, '自由飞翔', '唱歌🎤、跳舞💃、乒乓球🏓');
INSERT INTO `personal_info` (`user_id`, `gender`, `age`, `school`, `college`, `major`, `sno`, `native_place`, `project_num`, `fans_num`, `asset_num`, `about`, `avatar_url`, `name`, `techang`) VALUES (63, b'0', 0, NULL, NULL, NULL, NULL, NULL, 0, 0, 0, '该用户很懒，没有填写任何说明！提示：用户首次登录建议完善个人信息', NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'project name',
  `project_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '项目时间：2025-1-1',
  `project_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '项目描述：',
  `stack` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '技术栈：C/C++',
  `result` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '成果：',
  PRIMARY KEY (`id`),
  KEY `project_user_user_id` (`user_id`),
  CONSTRAINT `project_user_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of project
-- ----------------------------
BEGIN;
INSERT INTO `project` (`id`, `user_id`, `name`, `project_time`, `project_description`, `stack`, `result`) VALUES (7, 61, '项目A', '项目时间：2025-1-1', '项目描述：在项目A中负责了前端的搭建', '技术栈：HTML/CSS/JS', '成果：校级优秀项目');
INSERT INTO `project` (`id`, `user_id`, `name`, `project_time`, `project_description`, `stack`, `result`) VALUES (8, 61, '项目B', '项目时间：2025-3-1', '项目描述：在项目B中负责了安卓APP开发', '技术栈：Android', '成果：国家级大创结题');
COMMIT;

-- ----------------------------
-- Table structure for timeline
-- ----------------------------
DROP TABLE IF EXISTS `timeline`;
CREATE TABLE `timeline` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '2025-01-01',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '请输入',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `user_timeline_user_id` (`user_id`),
  CONSTRAINT `user_timeline_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of timeline
-- ----------------------------
BEGIN;
INSERT INTO `timeline` (`id`, `user_id`, `time`, `content`) VALUES (37, 61, '2025-03-01', '完成了10学分的课程！');
INSERT INTO `timeline` (`id`, `user_id`, `time`, `content`) VALUES (38, 61, '2024-09-01', '进入了中国人民大学，研究生活开始了！');
INSERT INTO `timeline` (`id`, `user_id`, `time`, `content`) VALUES (39, 61, '2024-06-01', '我毕业啦！时光如白驹过隙，转眼已至毕业的十字路口。回望这段旅程，最珍贵的行囊里装着师长的谆谆教诲、同窗的温暖笑靥，以及无数个为论文挑灯夜战的深夜。图书馆的晨光、操场的晚风、实验室的数据曲线，都在记忆里凝成发光的琥珀。感恩遇见的所有人与事，让我在探索知识的路上从未孤单，更教会我以谦卑之心拥抱未知。愿带着这份积淀继续前行，在更广阔的天地间保持少年热忱，以智慧为剑，以理想为帆，书写属于我们这代人的答卷。');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户ID，主键',
  `user_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名，用于登录，唯一',
  `phone` char(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号，唯一',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '邮箱，可用于找回密码，唯一',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `is_admin` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否为管理员，1是，0否',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `enable` bit(1) NOT NULL DEFAULT b'1' COMMENT '是否启用，0表示被注销或者被系统管理员封号',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE KEY `user_name` (`user_name`) USING BTREE,
  UNIQUE KEY `phone` (`phone`) USING BTREE,
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` (`user_id`, `user_name`, `phone`, `email`, `create_time`, `is_admin`, `password`, `enable`) VALUES (61, 'aaa', '18912345678', 'aaa@ruc.edu.cn', '2025-03-08 02:46:01', b'0', '25f9e794323b453885f5181f1b624d0b', b'1');
INSERT INTO `user` (`user_id`, `user_name`, `phone`, `email`, `create_time`, `is_admin`, `password`, `enable`) VALUES (63, 'admin', NULL, NULL, '2025-03-08 02:49:21', b'1', '25f9e794323b453885f5181f1b624d0b', b'1');
COMMIT;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `insert_new_user`;
delimiter ;;
CREATE TRIGGER `web_system`.`insert_new_user` AFTER INSERT ON `user` FOR EACH ROW INSERT INTO personal_info(user_id) VALUES(new.user_id)
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
