package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * チケットメモ（履歴）一覧取得レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class TicketMemoListResponseDto {
	/** チケットメモ（履歴）一覧 */
	private List<TicketMemoDto> memoList;
}