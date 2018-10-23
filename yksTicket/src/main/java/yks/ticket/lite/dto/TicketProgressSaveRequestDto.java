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
public class TicketProgressSaveRequestDto {
	/** プロジェクトID */
	private Long project_id;
	/** チケット進捗一覧 */
	private List<TicketProgressDto> progressList;
}