package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * チケット優先順位エンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketPriorityEntity extends CommonEntity {
	/** プロジェクトID */
	private Long project_id;
	/** 優先順位ID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** 優先順位名称 */
	private String name;
	/** 有効フラグ */
	private String available;
}