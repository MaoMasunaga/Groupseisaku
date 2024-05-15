CREATE TABLE `test` (
	`id` SERIAL COMMENT 'id',
	`student_no` VARCHAR(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学生番号',
	`subject_cd` CHAR(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '科目コード',
	`school_cd` CHAR(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校コード',
	`no` INT(10) NOT NULL COMMENT '回数',
	`point` INT(10) COMMENT '得点',
	`class_num` VARCHAR(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT 'クラス番号',
	PRIMARY KEY (`student_no`,`subject_cd`,`school_cd`,`no`)
) ENGINE=InnoDB;