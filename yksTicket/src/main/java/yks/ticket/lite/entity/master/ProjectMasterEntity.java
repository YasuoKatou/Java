package yks.ticket.lite.entity.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yks.ticket.lite.entity.CommonEntity;

/**
 * プロジェクトマスタエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectMasterEntity extends CommonEntity {
	/** プロジェクト識別子 */
	private Long id;
	/** プロジェクト名称 */
	private String name;
	/** プロジェクトの説明 */
	private String description;
	/** プロジェクト管理者 */
	private Long manager_id;
	/** プロジェクト完了 */
	private String terminated;
	/** 公開 */
	private String opened;

	/** プロジェクト管理者情報 */
	private UserMasterEntity manager;
}