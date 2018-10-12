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
}
