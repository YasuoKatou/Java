package yks.ticket.lite.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ロールマスタ取得レスポンスDto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RollMasterResponseDto {
	/** ロールグループ一覧. */
	List<Group> group;

	/**
	 * ロールグループ.
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	@Builder
	@Data
	public static class Group {
		/** ロールグループID */
		private Long id;
		/** ロールグループ名称 */
		private String name;
	}

	/** ロール項目一覧. */
	List<Item> item;

	/**
	 * ロール項目.
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	@Builder
	@Data
	public static class Item {
		/** ロール項目ID */
		private Long id;
		/** ロールグループID */
		private Long group_id;
		/** ロール項目名称 */
		private String name;
	}
}