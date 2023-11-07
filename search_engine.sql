CREATE TABLE `doc`  (
                        `doc_id` int NOT NULL AUTO_INCREMENT COMMENT '文档id',
                        `title` varchar(255) NOT NULL COMMENT '文档标题',
                        `desc` varchar(255) NOT NULL COMMENT '文档描述',
                        `url` varchar(1024) NOT NULL COMMENT '文档url',
                        `score` int(255) NOT NULL COMMENT '文档权重',
                        PRIMARY KEY (`doc_id`)
);

CREATE TABLE `user`  (
                         `id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
                         `username` varchar(255) NULL COMMENT '用户名',
                         `password` varchar(255) NULL COMMENT '用户密码',
                         PRIMARY KEY (`id`)
);

