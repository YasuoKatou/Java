package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケット種類Dto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketKindDto {
	/** プロジェクトID */
	private Long project_id;
	/** 種類ID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** 種類名称 */
	private String name;
	/** バージョンNo */
	private Integer versionNo;
}