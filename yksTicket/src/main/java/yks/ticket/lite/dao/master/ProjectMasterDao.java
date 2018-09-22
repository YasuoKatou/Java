package yks.ticket.lite.dao.master;

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
}