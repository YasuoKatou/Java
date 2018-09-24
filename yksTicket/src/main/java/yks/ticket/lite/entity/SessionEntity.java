package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * セッション管理エンティティ.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity extends CommonEntity {
	/** セッションID */
	private String session_id;
	/** ユーザ識別子 */
	private Long user_id;
}