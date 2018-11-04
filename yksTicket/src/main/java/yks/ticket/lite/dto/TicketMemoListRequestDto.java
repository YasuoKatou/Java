package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケットメモ（履歴）一覧取得リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketMemoListRequestDto {
	/** チケットID */
	private Long ticket_id;
}