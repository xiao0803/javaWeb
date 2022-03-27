CREATE TABLE `t_website_info` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `website_name` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站名称',
  `website_url` VARCHAR(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站地址',
  `login_name` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录名称',
  `login_pwd` VARCHAR(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '登录密码',
  `terminal_type` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '终端类型:PC网站、PC应用、APP、微信公众号、微信小程序等',
  `category1` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站一级分类:普通网站、开发工具等',
  `category2` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '网站二级分类:政府平台、版本控制等',
  `other_info` VARCHAR(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '其它信息',
  `remark` VARCHAR(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `backups_flag` TINYINT(2) DEFAULT '1' COMMENT '是否备份；1-否、2-是',
  `status_value` TINYINT(2) DEFAULT '1' COMMENT '状态；1-正常、2-删除',
  `modificator_id` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '修改者ID',
  `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creator_id` VARCHAR(64) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '创建者ID',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='网站信息'

