package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザ情報Dto.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends RequestHeaderDto {
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
	private Long language_id;
	/** 言語名 */
	private String language_name;
	/** 言語国名 */
	private String language_country;
}