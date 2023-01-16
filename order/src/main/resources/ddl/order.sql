CREATE TABLE `order` (
       `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
       `product_name` varchar(45) NOT NULL COMMENT '产品名称',
       `count` bigint(20) NOT NULL COMMENT '数量',
       `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
       `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
       PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='订单表';
