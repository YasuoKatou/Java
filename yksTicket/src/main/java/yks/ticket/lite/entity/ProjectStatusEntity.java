package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * プロジェクトスタータス管理エンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectStatusEntity extends CommonEntity {
	/** プロジェクトID */
	private Long projectId;
	/** ステータスID */
	private Long id;
	/** 表示順 */
	private int dispSeq;
	/** スタータス名称 */
	private String name;
	/** 有効フラグ */
	private String available;
}