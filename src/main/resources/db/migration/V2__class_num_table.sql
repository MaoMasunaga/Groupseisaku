CREATE TABLE `class_num` (
	`id` SERIAL NOT NULL COMMENT 'id',
	`school_cd` CHAR(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '学校コード',
	`class_num` VARCHAR(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'クラス番号',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB;