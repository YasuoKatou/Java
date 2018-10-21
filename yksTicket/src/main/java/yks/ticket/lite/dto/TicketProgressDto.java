package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケット進捗Dto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketProgressDto {
	/** プロジェクトID */
	private Long project_id;
	/** 進捗ID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** 進捗名称 */
	private String name;
	/** バージョンNo */
	private Integer versionNo;
}