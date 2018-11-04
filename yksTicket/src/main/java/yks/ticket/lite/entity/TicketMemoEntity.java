package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * チケットメモエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class TicketMemoEntity extends CommonEntity {
	/** メモID */
	private Long id;
	/** チケットID */
	private Long ticket_id;
	/** メモ内容 */
	private String memo;
	/** ルートメモID */
	private Long root_memo_id;
	/** 親メモID */
	private Long parent_memo_id;
}