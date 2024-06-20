create database student;
use student;
create table sys_user
(
    id              bigint primary key not null auto_increment comment 'ID',
    username        varchar(50)        not null comment '用户名',
    nickname        varchar(50)        not null comment '昵称',
    `password`      varchar(100)       not null comment '密码',
    email           varchar(100)       not null comment '邮箱',
    sex             tinyint            not null default 0 comment '性别 - 0:保密 1:男 2:女',
    del_flag        tinyint            not null default 0 comment '是否注销 - 0:未注销 1:注销',
    last_login_time datetime           null comment '最后登录时间',
    create_time     datetime           not null comment '注册时间',
    update_time     datetime           not null comment '修改时间'
) comment '系统用户表';
