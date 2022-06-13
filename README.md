# RottenSearch搜索引擎



## 技术选型

**使用语言**

- Java 17

**核心框架**

- SpringBoot 2.6.7

**数据库**

- mysql 8.0.28
- druid 1.2.9
- mybatis-plus 3.5.1
- redis 6

**测试**

- junit 4.13.2

**依赖管理**

- maven

**工具**

- lombok
- devtools
- jieba-analysis 1.0.2
- fastjson 2.0.2
- lucene 8.11.1

# 数据库设计

<img src="https://www.yumoyumo.top/wp-content/uploads/2022/05/image-20220509222424853.png" style="zoom:67%;" />

该数据库主要包含三个主表：user用户表，star收藏夹表，doc文档表；然后包含两个关系表user_stars，stars_doc
因为悟空数据集只包含url和desc两个字段，因此doc表的title字段默认为空字符串

**SQL文件：**

```sql
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
`user_id` int(0) NOT NULL COMMENT '用户id',
`username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户名',
`password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '' COMMENT '用户密码',
PRIMARY KEY (`user_id`) USING BTREE
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
   CONSTRAINT `USER_ID` FOREIGN KEY (`uid`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```

# 接口设计

## 中间件

### 响应体增强

允许在执行 **@ResponseBody** 或 **@ResponseEntity** 控制器方法之后，但在使用 **HttpMessageConverter** 写入正文之前自定义响应。

实现可以直接在 **RequestMappingHandlerAdapter** 和 **ExceptionHandlerExceptionResolver** 中注册，或者更可能使用 **@ControllerAdvice** 进行注释，在这种情况下，它们都将被自动检测到。

#### JsonResponseBodyAdvice

Json响应体增强

![](https://www.yumoyumo.top/wp-content/uploads/2022/06/image-20220610142218545.png)

#### **RsaEncodeResponseBodyAdvice**

响应体加密增强

![](https://www.yumoyumo.top/wp-content/uploads/2022/06/image-20220610142236853.png)

## 接口

### /search/query

![](https://www.yumoyumo.top/wp-content/uploads/2022/06/image-20220610130925811.png)



### /search/queryDoc

![](https://www.yumoyumo.top/wp-content/uploads/2022/06/image-20220610135554510.png)

### /user/login



### /user/regist



### /star/createstars



### /star/add



### /star/deletestar



### /star/deletedoc



### /star/modifyStar



### /star/getStars

