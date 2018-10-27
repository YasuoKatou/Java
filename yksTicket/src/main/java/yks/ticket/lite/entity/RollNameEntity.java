package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ロール名称エンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class RollNameEntity extends CommonEntity {
	/** ロール名称識別子 */
	private Long id;
	/** ロール名称 */
	private String name;
	/** ロール説明 */
	private String description;
}