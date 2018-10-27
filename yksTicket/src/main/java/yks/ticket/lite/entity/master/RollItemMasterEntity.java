package yks.ticket.lite.entity.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yks.ticket.lite.entity.CommonEntity;

/**
 * ロール項目マスタエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class RollItemMasterEntity extends CommonEntity {
	/** ロール識別子 */
	private Long id;
	/** ロール名称 */
	private String name;
	/** ロールグループ識別子 */
	private Long group_id;
}