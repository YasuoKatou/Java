package yks.ticket.lite.dao.master;

import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * ユーザマスタDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface UserMasterDao {
	/**
	 * レコードを登録する.
	 * 
	 * @param entity ユーザマスタエンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(UserMasterEntity entity);

	/**
	 * ユーザマスタをログインIDで取得する.
	 * 
	 * @param entity ユーザマスタエンティティ
	 * @return ユーザマスタエンティティ
	 * @since 0.0.1
	 */
	UserMasterEntity findByLoginId(UserMasterEntity entity);
}