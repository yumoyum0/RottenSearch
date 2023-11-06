-- ----------------------------
-- Table structure for doc
-- ----------------------------
DROP TABLE IF EXISTS `doc`;
CREATE TABLE `doc`  (
`doc_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '文档id',
`title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '文档标题',
`desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文档描述',
`url` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '文档url',
`score` int(0) NULL DEFAULT 0 COMMENT '文档权重',
PRIMARY KEY (`doc_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for star
-- ----------------------------
DROP TABLE IF EXISTS `star`;
CREATE TABLE `star`  (
`star_id` int(0) NOT NULL COMMENT '收藏夹id',
`star_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '收藏夹名',
PRIMARY KEY (`star_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for stars_doc
-- ----------------------------
DROP TABLE IF EXISTS `stars_doc`;
CREATE TABLE `stars_doc`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `sid` int(0) NULL DEFAULT NULL COMMENT '关联到star表的star_id',
  `did` int(0) NOT NULL COMMENT '关联到doc表的doc_id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `DOC_ID`(`did`) USING BTREE,
  INDEX `STAR_ID`(`sid`) USING BTREE,
  CONSTRAINT `DOC_ID` FOREIGN KEY (`did`) REFERENCES `doc` (`doc_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `STAR_ID` FOREIGN KEY (`sid`) REFERENCES `star` (`star_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
`id` int(0) NOT NULL COMMENT '用户id',
`username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户名',
`password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户密码',
PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

-- ----------------------------
-- Table structure for user_stars
-- ----------------------------
DROP TABLE IF EXISTS `user_stars`;
CREATE TABLE `user_stars`  (
   `id` int(0) NOT NULL AUTO_INCREMENT,
   `sid` int(0) NOT NULL COMMENT '关联到star表的star_id',
   `uid` int(0) NOT NULL COMMENT '关联到user表的user_id',
   PRIMARY KEY (`id`) USING BTREE,
   INDEX `USER_ID`(`uid`) USING BTREE,
   CONSTRAINT `USER_ID` FOREIGN KEY (`uid`) REFERENCES `user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;

