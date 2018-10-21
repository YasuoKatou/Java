package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * チケット進捗エンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketProgressEntity extends CommonEntity {
	/** プロジェクトID */
	private Long project_id;
	/** 進捗ID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** 進捗名称 */
	private String name;
	/** 有効フラグ */
	private String available;
}