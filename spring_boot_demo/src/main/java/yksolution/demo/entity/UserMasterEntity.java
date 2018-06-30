package yksolution.demo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class UserMasterEntity {
  /** ユーザId */
  private String userId;
  /** パスワード */
  private String passwd;
  /** 権限コード */
  private String authority;
}