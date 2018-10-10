package yks.ticket.lite.dao;

public interface DBInitDao {
	/**
	 * プロジェクトマスタテーブルを生成する.
	 * @since 0.0.1
	 */
	void createProjectMaster();

	/**
	 * プロジェクトマスタテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropProjectMaster();

	/**
	 * ユーザマスタテーブルを生成する.
	 * @since 0.0.1
	 */
	void createUserMaster();

	/**
	 * ユーザマスタテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropUserMaster();

	/**
	 * 言語マスタテーブルを生成する.
	 * @since 0.0.1
	 */
	void createLanguageMaster();

	/**
	 * 言語マスタテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropLanguageMaster();

	/**
	 * ロールグループマスタを生成する.
	 * @since 0.0.1
	 */
	void createRollGroupMaster();

	/**
	 * ロールグループマスタを削除する.
	 * @since 0.0.1
	 */
	void dropRollGroupMaster();

	/**
	 * ロール項目マスタを生成する.
	 * @since 0.0.1
	 */
	void createRollItemMaster();

	/**
	 * ロール項目マスタを削除する.
	 * @since 0.0.1
	 */
	void dropRollItemMaster();

	/**
	 * セッション管理テーブルを生成する.
	 * @since 0.0.1
	 */
	void createSessionTran();

	/**
	 * セッション管理テーブルを削除する.
	 * @since 0.0.1
	 */
	void dropSessionTran();

	/**
	 * ロール名称テーブルを生成する.
	 * @since 0.0.1
	 */
	void createRollNameTran();

	/**
	 * ロール名称テーブルを削除する.
	 * @since 0.0.1
	 */
	void dropRollNameTran();

	/**
	 * ロール設定テーブルを生成する.
	 * @since 0.0.1
	 */
	void createRollSettingTran();

	/**
	 * ロール設定テーブルを削除する.
	 * @since 0.0.1
	 */
	void dropRollSettingTran();

	/**
	 * チケットテーブルを生成する.
	 * @since 0.0.1
	 */
	void createTicketTran();

	/**
	 * チケットテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropTicketTran();

	/**
	 * チケットメモテーブルを生成する.
	 * @since 0.0.1
	 */
	void createTicketMemoTran();

	/**
	 * チケットメモテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropTicketMemoTran();

	/**
	 * 履歴チケットテーブルを生成する.
	 * @since 0.0.1
	 */
	void createTicketHistory();

	/**
	 * 履歴チケットテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropTicketHistory();

	/**
	 * 履歴チケットメモテーブルを生成する.
	 * @since 0.0.1
	 */
	void createTicketMemoHistory();

	/**
	 * 履歴チケットメモテーブルを削除する.
	 * @since 0.0.1
	 */
	void dropTicketMemoHistory();
}