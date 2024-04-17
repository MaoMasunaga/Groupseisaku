CREATE TABLE `school` (
	`id` SERIAL NOT NULL COMMENT 'id',
	`school_cd` CHAR(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校コード',
	`name` VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '学校名',
	PRIMARY KEY (`school_cd`)
) ENGINE=InnoDB;