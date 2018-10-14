package yks.ticket.lite.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yks.ticket.lite.dto.ProjectStatusListResponseDto.Status;

/**
 * プロジェクトスタータス更新リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusUpdateRequestDto extends RequestHeaderDto {
	/** プロジェクトID */
	private Long project_id;
	/** プロジェクトステータス一覧 */
	private List<Status> statusList;
}