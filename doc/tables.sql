
-- セッション情報
-- Springの公式
https://github.com/spring-projects/spring-session/blob/main/spring-session-jdbc/src/main/resources/org/springframework/session/jdbc/schema-mysql.sql

-- ユーザー認証情報(MySQL版)
DROP TABLE IF EXISTS user;
CREATE TABLE user (
  id BIGINT AUTO_INCREMENT              COMMENT 'ユーザーID',
  username VARCHAR(80) NOT NULL         COMMENT 'ユーザー名',
  password VARCHAR(256) NOT NULL        COMMENT 'パスワード',
  email VARCHAR(120) NOT NULL           COMMENT 'メールアドレス',
  roles VARCHAR(120)                    COMMENT 'ロール',
  locked BOOLEAN NOT NULL DEFAULT 0     COMMENT 'ロックフラグ 1:ロック',
  expired BOOLEAN NOT NULL DEFAULT 0    COMMENT '無効フラグ 1:無効',
  credentialsExpired BOOLEAN NOT NULL DEFAULT 0 COMMENT '認証情報無効フラグ 1:無効',
  created_by VARCHAR(80) NOT NULL       COMMENT '作成者',
  created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_by VARCHAR(80) NOT NULL       COMMENT '更新者',
  updated_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  deleted BOOLEAN NOT NULL DEFAULT 0    COMMENT '論理削除フラグ 1:無効',
  PRIMARY KEY (id)
)
COMMENT = 'ユーザー';

ALTER TABLE user ADD CONSTRAINT UNIQUE KEY UKEY_user_email (email);

