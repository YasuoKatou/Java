package yks.ticket.lite.entity;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * チケットエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketEntity extends CommonEntity {
	/** チケットID */
	private Long id;
	/** チケットタイトル */
	private String title;
	/** チケットの説明 */
	private String description;
	/** チケットステータスID */
	private Long status_id;
	/** チケットステータスエンティティ */
	private TicketStatusEntity status;
	/** チケット種類ID */
	private Long kind_id;
	/** チケット種類エンティティ */
	private TicketKindEntity kind;
	/** チケット優先順位ID */
	private Long priority_id;
	/** チケット優先順位エンティティ */
	private TicketPriorityEntity priority;
	/** 開始日 */
	private Date start_date;
	/** 終了日 */
	private Date finish_date;
	/** チケットの進捗ID */
	private Long progress_id;
	/** チケットの進捗エンティティ */
	private TicketProgressEntity progress;
	/** プロジェクトID */
	private Long project_id;
}