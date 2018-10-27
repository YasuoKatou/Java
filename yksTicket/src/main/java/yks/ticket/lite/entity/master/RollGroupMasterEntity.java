package yks.ticket.lite.entity.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import yks.ticket.lite.entity.CommonEntity;

/**
 * ロールグループマスタエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class RollGroupMasterEntity extends CommonEntity {
	/** ロールグループ識別子 */
	private Long id;
	/** ロールグループ名称 */
	private String name;
}