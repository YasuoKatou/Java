package yks.ticket.lite.dao.master;

import yks.ticket.lite.entity.master.RollGroupMasterEntity;

/**
 * ロールグループマスタDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface RollGroupMasterDao {
	/**
	 * レコードを登録する.
	 * 
	 * @param entity ロールグループマスタエンティティ.
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(RollGroupMasterEntity entity);
}