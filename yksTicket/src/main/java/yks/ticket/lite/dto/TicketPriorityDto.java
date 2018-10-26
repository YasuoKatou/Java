package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケット優先順位Dto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketPriorityDto {
	/** プロジェクトID */
	private Long project_id;
	/** 優先順位ID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** 優先順位名称 */
	private String name;
	/** バージョンNo */
	private Integer versionNo;
}