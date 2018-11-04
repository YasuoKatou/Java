package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * チケット取得レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketResponseDto {
	/** チケットDto */
	private TicketDto ticketDto;
}