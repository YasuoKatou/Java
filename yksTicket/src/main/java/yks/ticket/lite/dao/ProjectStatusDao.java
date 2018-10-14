package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.ProjectStatusEntity;

/**
 * プロジェクトステータス管理テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface ProjectStatusDao {
	/**
	 * プロジェクトステータス一覧を取得する.
	 *
	 * @param projectId プロジェクトID
	 * @return プロジェクトスタータス管理エンティティ一覧
	 * @since 0.0.1
	 */
	List<ProjectStatusEntity> findByProject(@Param("projectId") Long projectId);

	/**
	 * 指定プロジェクトのステータスを全て使用不可に設定する.
	 *
	 * @param projectId プロジェクトID
	 * @return 更新レコード数
	 * @since 0.0.1
	 */
	int updateProject2NotUse(@Param("projectId") Long projectId);

	/**
	 * 指定プロジェクトのステータスを更新する.
	 *
	 * @param entity プロジェクトスタータス管理エンティティ.
	 * @return 1（更新件数）
	 * @since 0.0.1
	 */
	int updateStatus(ProjectStatusEntity entity);

	/**
	 * 指定プロジェクトにステータスを新規登録する.
	 *
	 * @param entity プロジェクトスタータス管理エンティティ.
	 * @return 1（登録件数）
	 * @since 0.0.1
	 */
	int appendStatus(ProjectStatusEntity entity);

	/**
	 * 次のステータスIDを取得する.
	 *
	 * @param projectId プロジェクトID
	 * @return 次のステータスID
	 * @since 0.0.1
	 */
	Long getNextStatusId(@Param("projectId") Long projectId);
}