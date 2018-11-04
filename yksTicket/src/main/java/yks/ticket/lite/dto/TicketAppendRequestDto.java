package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * チケット登録リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketAppendRequestDto extends RequestHeaderDto {
	/** チケットDto. */
	private TicketDto ticket;
}