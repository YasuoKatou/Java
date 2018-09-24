package yks.ticket.lite.dao.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * プロジェクト一覧取得要求Dto.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectListRequestDto {
	/** プロジェクト進行中 */
	private String alive;
}