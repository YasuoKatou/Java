package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * チケットステータス登録リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketStatusSaveRequestDto {
	/** プロジェクトID */
	private Long project_id;
	/** チケットステータス一覧 */
	private List<TicketStatusDto> statusList;
}