package yks.ticket.lite.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * プロジェクトスタータス一覧レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class ProjectStatusListResponseDto {
	/** プロジェクトステータス一覧 */
	private List<Status> statusList;

	@Builder
	@Data
	public static class Status {
		/** ステータスID */
		private Long id;
		/** スタータス名称 */
		private String name;
	}
}
