package yks.ticket.lite.dto;

import java.util.Date;

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
	private Date start_date;
	/** 終了日 */
	private Date finish_date;
	/** チケットの進捗 */
	private Long progress_id;
	/** チケット種類ID */
	private Long kind_id;
	/** チケット優先順位ID */
	private Long priority_id;
	/** プロジェクトID */
	private Long project_id;
	/** 作成日時 */
	private Date createDate;
	/** 作成者ID */
	private Long createUserId;
	/** 更新日時 */
	private Date updateDate;
	/** 更新者ID */
	private Long updateUserId;
	/** バージョンNo */
	private Integer versionNo;
}