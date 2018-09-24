package yks.ticket.lite.dto.master;

import lombok.Builder;
import lombok.Data;
import yks.ticket.lite.dto.RequestHeaderDto;

/**
 * プロジェクトDTO.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class ProjectDto extends RequestHeaderDto {
	/** プロジェクト識別子 */
	private Long id;
	/** プロジェクト名称 */
	private String name;
	/** プロジェクトの説明 */
	private String description;
	/** プロジェクト管理者 */
	private Long manager_id;
	/** プロジェクト進行中 */
	private String alive;
	/** 公開 */
	private String opened;
	/** プロジェクト管理者（性） */
	private String manager_name1;
	/** プロジェクト管理者（名） */
	private String manager_name2;
}