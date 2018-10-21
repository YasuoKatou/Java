package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケットステータスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketStatusDto {
	/** プロジェクトID */
	private Long project_id;
	/** チケットID */
	private Long id;
	/** 表示順 */
	private Integer disp_seq;
	/** ステータス名称 */ 
	private String name;
	/** バージョンNo */
	private Integer versionNo;
}