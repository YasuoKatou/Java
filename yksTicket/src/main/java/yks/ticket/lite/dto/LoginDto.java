package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ログインDto.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
	/** ユーザ識別子 */
	private Long id;
	/** 性 */
	private String name1;
	/** 名 */
	private String name2;
	/** EMail */
	private String email;
	/** 言語ID */
	private Long language_id;
}