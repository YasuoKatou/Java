package yks.ticket.lite.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ロール設定情報登録リクエストDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollRecordRequestDto extends RequestHeaderDto {
	/** ロール名称ID */
	private Long id;
	/** ロール名称 */
	private String name;
	/** 説明 */
	private String descript;
	/** ロール設定項目一覧 */
	private List<RollSetting> items;

	/**
	 * ロール設定項目
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class RollSetting {
		/** ロール項目ID */
		private Long id;
		/** ロールグループID */
		private Long group_id;
	}
}