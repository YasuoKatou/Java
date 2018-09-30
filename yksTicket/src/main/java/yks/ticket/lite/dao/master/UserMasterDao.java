package yks.ticket.lite.dao.master;

import java.util.List;

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
	 * レコードを更新する.
	 * 
	 * @param entity ユーザマスタエンティティ
	 * @return 1 (更新レコード数)
	 * @since 0.0.1
	 */
	int update(UserMasterEntity entity);

	/**
	 * ユーザマスタをログインIDで取得する.
	 * 
	 * @param entity ユーザマスタエンティティ
	 * @return ユーザマスタエンティティ
	 * @since 0.0.1
	 */
	UserMasterEntity findByLoginId(UserMasterEntity entity);

	/**
	 * ユーザの一覧を取得する.
	 * 
	 * @return ユーザマスタエンティティ一覧
	 * @since 0.0.1
	 */
	List<UserMasterEntity> findListAll();
}