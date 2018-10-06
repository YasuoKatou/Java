package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ロール設定レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class RollSettingResponseDto {
	/** ロール項目ID */
	private Long id;
	/** ロールグループID */
	private Long group_id;
}