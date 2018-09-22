package yks.ticket.lite.entity.master;

import lombok.Builder;
import lombok.Data;
import yks.ticket.lite.entity.CommonEntity;

/**
 * ユーザマスタエンティティ
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class UserMasterEntity extends CommonEntity {
	/** ユーザ識別子 */
	private Long id;
	/** ログインID */
	private String login_id;
	/** パスワード */
	private String passwd;
	/** 性 */
	private String name1;
	/** 名 */
	private String name2;
	/** EMail */
	private String email;
	/** 言語ID */
	private long language_id;
}