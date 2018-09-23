package yks.ticket.lite.dao.master;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.master.ProjectMasterEntity;

/**
 * プロジェクトマスタDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface ProjectMasterDao {
	/**
	 * レコードを登録する.
	 * 
	 * @param entity プロジェクトマスタエンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(ProjectMasterEntity entity);

	/**
	 * プロジェクトの一覧を取得する.
	 * 
	 * @param terminated 終了したプロジェクトを含む／含まないを制御
	 * @return プロジェクトマスタエンティティ一覧
	 * @since 0.0.1
	 */
	List<ProjectMasterEntity> findProjects(@Param("terminated") String terminated);
}