package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケットDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketDto {
	/** チケットID */
	private Long id;
	/** チケットタイトル */
	private String title;
	/** チケットの説明 */
	private String description;
	/** チケットステータスID */
	private Long status_id;
	/** 開始日 */
	private String start_date;
	/** 終了日 */
	private String finish_date;
	/** チケットの進捗 */
	private Long progress_id;
	/** チケット種類ID */
	private Long kind_id;
	/** チケット優先順位ID */
	private Long priority_id;
	/** プロジェクトID */
	private Long project_id;
	/** バージョンNo */
	private Integer versionNo;
}