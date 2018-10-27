package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * チケット優先順位登録リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketPrioritySaveRequestDto {
	/** プロジェクトID */
	private Long project_id;
	/** チケット優先順位一覧 */
	private List<TicketPriorityDto> priorityList;
}