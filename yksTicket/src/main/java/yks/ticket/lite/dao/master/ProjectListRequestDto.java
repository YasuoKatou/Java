package yks.ticket.lite.dao.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yks.ticket.lite.dto.RequestHeaderDto;

/**
 * プロジェクト一覧取得要求Dto.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectListRequestDto extends RequestHeaderDto {
	/** プロジェクト進行中 */
	private String alive;
}