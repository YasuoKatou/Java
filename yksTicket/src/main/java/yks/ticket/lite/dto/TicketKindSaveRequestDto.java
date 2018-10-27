package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * チケット種類登録リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketKindSaveRequestDto {
	/** プロジェクトID */
	private Long project_id;
	/** チケット種類一覧 */
	private List<TicketKindDto> kindList;
}