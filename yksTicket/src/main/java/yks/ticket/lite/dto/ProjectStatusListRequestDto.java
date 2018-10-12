package yks.ticket.lite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * プロジェクトスタータス一覧取得リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusListRequestDto extends RequestHeaderDto {
	/** プロジェクトID */
	private Long project_id;
}