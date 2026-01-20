CREATE DATABASE IF NOT EXISTS `demo_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `demo_db`;

DROP TABLE IF EXISTS `calc_log`;

CREATE TABLE `calc_log` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `param_a` int(11) NOT NULL COMMENT '入参A',
                            `param_b` int(11) NOT NULL COMMENT '入参B',
                            `result_c` int(11) NOT NULL COMMENT '结果C',
                            `call_time` datetime NOT NULL COMMENT '调用时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_params_time` (`param_a`,`param_b`,`call_time`) USING BTREE,
                            CONSTRAINT `chk_param_a` CHECK (`param_a` > 0),
                            CONSTRAINT `chk_param_b` CHECK (`param_b` > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计算日志表';