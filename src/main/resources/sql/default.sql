CREATE TABLE `security`.`users` (
                                    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '이용자의 내부 ID 정보',
                                    `username` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT '이용자의 Identity 정보를 특정할 수있는 이름',
                                    `password` VARCHAR(512) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT '이용자의 Identity정보의 소유를 확인해주는 비밀번호',
                                    `roles` VARCHAR(1000) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT '이용자의 역할정보',
                                    PRIMARY KEY (`id`),
                                    UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = '이용자 정보';

/*admin 의 비밀번호는 security!#34 입니다.*/
INSERT INTO `security`.`users` (`username`, `password`, `roles`) VALUES ('admin', '$2a$10$WTQ94BCL4Igr4GmYaPytpOVyo/lbhgFl2RHdyRiRXKusPLEJNbPjO', 'member,admin');


CREATE TABLE `security`.`mfa` (
                                  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'MFA 내부 ID',
                                  `username` VARCHAR(50) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT 'MFA설정한 이용자 이름',
                                  `secret_key` VARCHAR(512) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT 'T-OTP를 위한 Secret Key',
                                  `type` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_bin' NOT NULL COMMENT 'MFA의 유형',
                                  PRIMARY KEY (`id`),
                                  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
    ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_bin
COMMENT = 'multi-factor authentication';