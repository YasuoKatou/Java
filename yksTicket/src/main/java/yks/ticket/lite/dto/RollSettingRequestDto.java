package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ロール設定情報取得リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class RollSettingRequestDto {
	/** ロール名称ID */
	private Long id;
}