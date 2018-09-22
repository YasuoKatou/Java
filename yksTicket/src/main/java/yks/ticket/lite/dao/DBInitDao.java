package yks.ticket.lite.dao;

public interface DBInitDao {
	/**
	 * プロジェクトマスタテーブルを生成する.
	 */
	void createProjectMaster();

	/**
	 * プロジェクトマスタテーブルを削除する.
	 */
	void dropProjectMaster();

	/**
	 * ユーザマスタテーブルを生成する.
	 */
	void createUserMaster();

	/**
	 * ユーザマスタテーブルを削除する.
	 */
	void dropUserMaster();

	/**
	 * 言語マスタテーブルを生成する.
	 */
	void createLanguageMaster();

	/**
	 * 言語マスタテーブルを削除する.
	 */
	void dropLanguageMaster();
}