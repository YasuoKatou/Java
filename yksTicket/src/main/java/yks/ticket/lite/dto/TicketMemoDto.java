package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケットメモ（履歴）Dto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketMemoDto {
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
	/** 作成日時 */
	private String createDate;
	/** 作成者ID */
	private Long createUserId;
	/** 更新日時 */
	private String updateDate;
	/** 更新者ID */
	private Long updateUserId;
	/** バージョンNo */
	private Integer versionNo;
}