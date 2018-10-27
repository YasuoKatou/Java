package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * チケットステータスエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class TicketStatusEntity extends CommonEntity {
	/** プロジェクトID */
	private Long project_id;
	/** スタータスID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** ステータス名称 */
	private String name;
	/** 有効フラグ */
	private String available;
}