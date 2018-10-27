package yks.ticket.lite.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 初期データ投入情報Dto.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApInitData {
	/** 投入データ一覧 */
	private List<ApInitTable> initDataList;

	/**
	 * テーブル投入情報
	 *
	 * @author yasuokatou (YKS)
	 * @since 0.0.1
	 */
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class ApInitTable {
		/** タイトル */
		private String title;
		/** Daoクラス名 */
		private String dao;
		/** メソッド名 */
		private String method;
		/** エンティティクラス名 */
		private String entity;
		/** 投入データ */
		private List<Map<String, Object>> values;
	}}