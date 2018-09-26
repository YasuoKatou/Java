package yks.ticket.lite.dao.master;

import yks.ticket.lite.entity.master.RollMasterEntity;

/**
 * ロールマスタDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface RollMasterDao {
	/**
	 * レコードを登録する.
	 * @param entity ロールマスタエンティティ.
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(RollMasterEntity entity);
}