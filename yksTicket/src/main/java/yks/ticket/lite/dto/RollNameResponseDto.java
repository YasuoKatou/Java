package yks.ticket.lite.dto;

import lombok.Builder;
import lombok.Data;

/**
 * ロール情報レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class RollNameResponseDto {
	/** ロール項目ID. */
	private Long id;
	/** ロール項目名称 */
	private String name;
}