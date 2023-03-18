
-- セッション情報
-- Springの公式
https://github.com/spring-projects/spring-session/blob/main/spring-session-jdbc/src/main/resources/org/springframework/session/jdbc/schema-mysql.sql

-- ユーザー認証情報(MySQL版)
DROP TABLE IF EXISTS user_account;
CREATE TABLE user_account (
  id BIGINT AUTO_INCREMENT                  COMMENT 'ユーザーID',
  username VARCHAR(80) NOT NULL             COMMENT 'ユーザー名',
  password VARCHAR(256) NOT NULL            COMMENT 'パスワード',
  email VARCHAR(120) NOT NULL               COMMENT 'メールアドレス',
  roles VARCHAR(120)                        COMMENT 'ロール',
  disabled_flag BOOLEAN NOT NULL DEFAULT 0  COMMENT '無効フラグ 1:無効',
  locked_flag BOOLEAN NOT NULL DEFAULT 0    COMMENT 'ロックフラグ 1:ロック',
  expired_flag BOOLEAN NOT NULL DEFAULT 0   COMMENT '期限切れフラグ 1:期限切れ',
  credentialsExpired_flag BOOLEAN NOT NULL DEFAULT 0 COMMENT '認証情報期限切れフラグ 1:期限切れ',
  created_by VARCHAR(80) NOT NULL           COMMENT '作成者',
  created_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  updated_by VARCHAR(80) NOT NULL           COMMENT '更新者',
  updated_at TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  deleted BOOLEAN NOT NULL DEFAULT 0        COMMENT '論理削除フラグ 1:無効',
  PRIMARY KEY (id)
)
COMMENT = 'ユーザー認証情報';

ALTER TABLE user_account ADD CONSTRAINT UNIQUE KEY UKEY_USER_ACCOUNT_USERNAME (username);

