package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * チケット関連マスタ取得レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketMastersResponseDto {
	/** チケットステータス一覧 */
	private List<TicketStatusDto> statusList;
	/** チケット進捗一覧 */
	private List<TicketProgressDto> progressList;
}