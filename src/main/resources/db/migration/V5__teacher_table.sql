CREATE TABLE `teacher` (
	`id` SERIAL COMMENT 'id',
	`tid` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '教員ID',
	`password` VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'パスワード',
	`name` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '氏名',
	`school_cd` CHAR(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '学校コード',
	PRIMARY KEY (`tid`)
) ENGINE=InnoDB;