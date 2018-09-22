package yks.ticket.lite.entity.master;

import lombok.Builder;
import lombok.Data;
import yks.ticket.lite.entity.CommonEntity;

/**
 * プロジェクトマスタエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
public class ProjectMasterEntity extends CommonEntity {
	/** プロジェクト識別子 */
	private Long id;
	/** プロジェクト名称 */
	private String name;
	/** プロジェクトの説明 */
	private String description;
	/** プロジェクトの管理者 */
	private long manager_id;
	/** 公開 */
	private String opened;
}