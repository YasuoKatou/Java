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
	 * セッション管理テーブルを生成する.
	 * @since 0.0.1
	 */
	void createSessionTran();

	/**
	 * セッション管理テーブルを削除する.
	 * @since 0.0.1
	 */
	void dropSessionTran();
}