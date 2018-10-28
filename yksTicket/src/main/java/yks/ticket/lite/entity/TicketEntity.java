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
	/** チケット種類ID */
	private Long kind_id;
	/** チケット優先順位ID */
	private Long priority_id;
	/** 開始日 */
	private Date start_date;
	/** 終了日 */
	private Date finish_date;
	/** チケットの進捗 */
	private Long progress_id;
	/** プロジェクトID */
	private Long project_id;
}