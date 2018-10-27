package yks.ticket.lite.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ロール設定エンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
public class RollSettingEntity extends CommonEntity {
	/** ロール名称識別子 */
	private Long rollNameId;
	/** ロール項目識別子 */
	private Long rollItemId;
	/** ロールグループID. */
	private long rollGroupId;
}