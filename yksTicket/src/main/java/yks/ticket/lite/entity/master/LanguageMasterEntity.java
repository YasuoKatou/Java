package yks.ticket.lite.entity.master;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import yks.ticket.lite.entity.CommonEntity;

/**
 * 言語マスタエンティティ.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LanguageMasterEntity extends CommonEntity {
	/** 言語識別子 */
	private Long id;
	/** 言語名 */
	private String name;
	/** 国名 */
	private String country;
	/** 備考 */
	private String remarks;
}