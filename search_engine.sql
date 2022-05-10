CREATE TABLE `doc`  (
  `doc_id` int NOT NULL AUTO_INCREMENT COMMENT '文档id',
  `title` varchar(255) NOT NULL COMMENT '文档标题',
  `desc` varchar(255) NOT NULL COMMENT '文档描述',
  `url` varchar(1024) NOT NULL COMMENT '文档url',
  `score` int(255) NOT NULL COMMENT '文档权重',
  PRIMARY KEY (`doc_id`)
);

CREATE TABLE `star`  (
  `star_id` int NOT NULL COMMENT '收藏夹id',
  `star_name` varchar(255) NOT NULL COMMENT '收藏夹名',
  PRIMARY KEY (`star_id`)
);

CREATE TABLE `stars_doc`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sid` int NULL COMMENT '关联到star表的star_id',
  `did` int NOT NULL COMMENT '关联到doc表的doc_id',
  PRIMARY KEY (`id`)
);

CREATE TABLE `user`  (
  `user_id` int(0) NOT NULL COMMENT '用户id',
  `username` varchar(255) NULL COMMENT '用户名',
  `password` varchar(255) NULL COMMENT '用户密码',
  PRIMARY KEY (`user_id`)
);

CREATE TABLE `user_stars`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `sid` int NOT NULL COMMENT '关联到star表的star_id',
  `uid` int NOT NULL COMMENT '关联到user表的user_id',
  PRIMARY KEY (`id`)
);

ALTER TABLE `stars_doc` ADD CONSTRAINT `DOC_ID` FOREIGN KEY (`did`) REFERENCES `doc` (`doc_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `stars_doc` ADD CONSTRAINT `STAR_ID` FOREIGN KEY (`sid`) REFERENCES `star` (`star_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `user_stars` ADD CONSTRAINT `USER_ID` FOREIGN KEY (`uid`) REFERENCES `user` (`user_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
ALTER TABLE `user_stars` ADD CONSTRAINT `STAR_ID` FOREIGN KEY (`sid`) REFERENCES `star` (`star_id`) ON DELETE RESTRICT ON UPDATE RESTRICT;

