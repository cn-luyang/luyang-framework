create table `system_dict`
(
    `id`        BIGINT not null COMMENT '主键ID',
    `dict_name` VARCHAR(128) default null COMMENT '字典名称',
    `dict_type` VARCHAR(128) default null COMMENT '字典类型',
    `remark`    VARCHAR(255) default null COMMENT '备注',
    primary key (`id`) using BTREE,
    unique index `uniq_dict_type` (`dict_type`) using BTREE COMMENT '字典类型唯一索引'
) ENGINE = INNODB default CHARSET = utf8mb4 collate = utf8mb4_general_ci ROW_FORMAT = dynamic COMMENT = '字典表';

create table `system_dict_item`
(
    `id`         BIGINT not null COMMENT '主键ID',
    `dict_type`  VARCHAR(128) default null COMMENT '字典类型',
    `item_name`  VARCHAR(128) default null COMMENT '字典项名称',
    `item_value` VARCHAR(128) default null COMMENT '字典项值',
    `item_sort`  INT          default 1 COMMENT '排序（升序）',
    `remark`     VARCHAR(255) default null COMMENT '备注',
    primary key (`id`) using BTREE
) ENGINE = INNODB default CHARSET = utf8mb4 collate = utf8mb4_general_ci ROW_FORMAT = dynamic COMMENT = '字典项表';
