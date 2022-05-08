DROP DATABASE IF EXISTS `novel`;
create database `novel` default character set = 'utf8';
USE `novel`;
DROP TABLE IF EXISTS `author`;
CREATE TABLE `author` (
                          `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `pen_name` varchar(20) NOT NULL UNIQUE COMMENT '笔名',
                          `tel_phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
                          `chat_account` varchar(50) DEFAULT NULL COMMENT 'QQ或微信账号',
                          `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
                          `work_direction` tinyint(4) DEFAULT NULL COMMENT '作品方向，0：男频，1：女频',
                          `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='作者表';


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`(
                       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                       `user_name` varchar(20) NOT NULL UNIQUE COMMENT '用户名',
                       `user_password` varchar(200) NOT NULL COMMENT '用户密码',
                       `tel_phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
                       `pic_url` varchar(200) COMMENT '用户头像(预留字段)',
                       `chat_account` varchar(50) DEFAULT NULL COMMENT 'QQ或微信账号',
                       `email` varchar(50) DEFAULT NULL COMMENT '电子邮箱',
                       `user_type` tinyint(4) DEFAULT 0 COMMENT '用户类型，0:普通用户、1:VIP、2:作者',
                       `status` tinyint(4) DEFAULT 0 COMMENT '0：正常，1：封禁',
                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                       `followed_count` bigint(20) DEFAULT 0 COMMENT '被关注数',
                       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

DROP TABLE IF EXISTS `follow_record`;
CREATE TABLE `follow_record`(
                                `user_id1`  bigint(20) NOT NULL,
                                `user_id2`  bigint(20) NOT NULL,
                                PRIMARY KEY (`user_id1`, user_id2),
                                FOREIGN KEY (`user_id1`) REFERENCES user(`id`),
                                FOREIGN KEY (`user_id2`) REFERENCES user(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='关注记录表';



DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `work_direction` tinyint(1) DEFAULT NULL COMMENT '作品方向，0：男频，1：女频''',
                        `pic_url` varchar(200) NOT NULL COMMENT '小说封面',
                        `book_name` varchar(80) NOT NULL COMMENT '小说名',
                        `author_id` bigint(20) DEFAULT NULL COMMENT '作者id',
                        `author_name` varchar(50) NOT NULL COMMENT '作者名',
                        `book_desc` varchar(2000) NOT NULL COMMENT '书籍描述',
                        `score` float NOT NULL COMMENT '评分，预留字段',
                        `book_status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '书籍状态，0：连载中，1：已完结',
                        `visit_count` bigint(20) DEFAULT '0' COMMENT '点击量',
                        `word_count` int(11) DEFAULT '0' COMMENT '总字数',
                        `comment_count` int(11) DEFAULT '0' COMMENT '评论数',
                        `last_index_update_time` datetime DEFAULT NULL COMMENT '最新目录更新时间',
                        `is_vip` tinyint(1) DEFAULT '0' COMMENT '是否收费，1：收费，0：免费',
                        `status` tinyint(1) DEFAULT '0' COMMENT '状态，0：入库，1：上架、、、预留字段',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        `is_recommend` boolean DEFAULT FALSE COMMENT '是否推荐',
                        `type_name` varchar(50) DEFAULT NULL COMMENT '分类名',
                        PRIMARY KEY (`id`),
                        UNIQUE KEY `key_uq_bookName_authorName` (`book_name`,`author_name`) USING BTREE,
                        KEY `key_lastIndexUpdateTime-book` (`last_index_update_time`) USING BTREE,
                        KEY `key_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1352 DEFAULT CHARSET=utf8mb4 COMMENT='小说表';

DROP TABLE IF EXISTS `book_index`;
CREATE TABLE `book_index`(
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                             `book_id` bigint(20) NOT NULL UNIQUE ,
                             `index_name` varchar(50) NOT NULL COMMENT '最新目录名',
                             `chapter_count` bigint(20) DEFAULT NULL COMMENT '章节数',
                             PRIMARY KEY (`id`),
                             FOREIGN KEY (`book_id`) REFERENCES book(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8mb4 COMMENT='小说-目录表';



DROP TABLE IF EXISTS `index_chapter`;
CREATE TABLE `index_chapter`(
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `index_id`  bigint(20) NOT NULL,
                                `chapter_name` varchar(50) DEFAULT NULL COMMENT '章节名',
                                `chapter_content` mediumtext NOT NULL COMMENT '章节内容',
                                PRIMARY KEY (`id`),
                                FOREIGN KEY (`index_id`) REFERENCES book_index(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='目录-章节表';

DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`(
                      `id` int(11) AUTO_INCREMENT COMMENT '标签ID',
                      `tag_name` varchar(50) DEFAULT NULL COMMENT '标签名',
                      `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                      PRIMARY KEY (`id`),
                      KEY `key_createTime` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='标签表';


DROP TABLE IF EXISTS `type`;
CREATE TABLE `type`(
                       `id` int(11) AUTO_INCREMENT COMMENT '分类ID',
                       `type_name` varchar(50) DEFAULT NULL COMMENT '分类名',
                       `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                       PRIMARY KEY (`id`),
                       KEY `key_createTime` (`create_time`) USING BTREE
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='类型表';


DROP TABLE IF EXISTS `tag-book`;
CREATE TABLE `tag-book`(
                           `tag_id` int(11) COMMENT '标签ID',
                           `book_id`  bigint(20) COMMENT '书籍ID',
                           PRIMARY KEY (`tag_id`, `book_id`),
                           FOREIGN KEY (`tag_id`) REFERENCES `tag`(`id`),
                           FOREIGN KEY (`book_id`) REFERENCES `book`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签-书籍表';


DROP TABLE IF EXISTS `book_comment`;
CREATE TABLE `book_comment`(
                               `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '评论ID',
                               `book_id` bigint(20) NOT NULL COMMENT '小说ID',
                               `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                               PRIMARY KEY (`id`),
                               FOREIGN KEY (`book_id`) REFERENCES book(`id`),
                               FOREIGN KEY (`user_id`) REFERENCES user(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='小说评论表';




DROP TABLE IF EXISTS `book_comment_reply`;
CREATE TABLE `book_comment_reply`(
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '回复评论ID',
                                     `book_comment_id` bigint(20) NOT NULL,
                                     `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                     PRIMARY KEY (`id`),
                                     FOREIGN KEY (`book_comment_id`) REFERENCES book_comment(`id`),
                                     FOREIGN KEY (`user_id`) REFERENCES user(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='小说评论回复表';





DROP TABLE IF EXISTS `chapter_comment`;
CREATE TABLE `chapter_comment`(
                                  `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '章节回复评论ID',
                                  `chapter_id` bigint(20) NOT NULL COMMENT '章节ID',
                                  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                  PRIMARY KEY (`id`),
                                  FOREIGN KEY (`chapter_id`) REFERENCES index_chapter(`id`),
                                  FOREIGN KEY (`user_id`) REFERENCES user(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='章节评论表';




DROP TABLE IF EXISTS `chapter_comment_reply`;
CREATE TABLE `chapter_comment_reply`(
                                        `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '章节回复评论ID',
                                        `chapter_comment_id` bigint(20) NOT NULL,
                                        `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                        PRIMARY KEY (`id`),
                                        FOREIGN KEY (`chapter_comment_id`) REFERENCES chapter_comment(`id`),
                                        FOREIGN KEY (`user_id`) REFERENCES user(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='章节评论回复表';




DROP TABLE IF EXISTS `chapter_barrage`;
CREATE TABLE `chapter_barrage`(
                                  `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT '弹幕ID',
                                  `chapter_id` bigint(20) NOT NULL COMMENT '章节ID',
                                  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                  PRIMARY KEY (`id`),
                                  FOREIGN KEY (`chapter_id`) REFERENCES index_chapter(`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='章节弹幕表';

DROP TABLE IF EXISTS `graphTimeCount`;
CREATE TABLE `graphTimeCount`(
                                 `id`  bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                 `year` year NOT NULL UNIQUE,
                                 `Authors` bigint(20) DEFAULT '0',
                                 `Vips` bigint(20) DEFAULT '0',
                                 `Books` bigint(20) DEFAULT '0',
                                 PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='图表检索表';

DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`(
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `admin_name` varchar(20) NOT NULL UNIQUE COMMENT '管理员名',
                                `admin_password` varchar(200) NOT NULL COMMENT '管理员密码',
                                `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='管理员表';


# 图表需要生成前几年的虚拟数据状态
INSERT INTO `graphTimeCount`(year) VALUE (CURDATE());
INSERT INTO `graphTimeCount`(year) VALUE (DATE_SUB(CURDATE(), interval 1 year));
INSERT INTO `graphTimeCount`(year) VALUE (DATE_SUB(CURDATE(), interval 2 year));
INSERT INTO `graphTimeCount`(year) VALUE (DATE_SUB(CURDATE(), interval 3 year));
INSERT INTO `graphTimeCount`(year) VALUE (DATE_SUB(CURDATE(), interval 4 year));
INSERT INTO `graphTimeCount`(year) VALUE (DATE_SUB(CURDATE(), interval 5 year));
#默认安全框架用户名为admin，密码为200013
INSERT INTO `administrator` VALUES (1, 'admin', '209c8cbc87216c83ce038747e2049917', NOW());
