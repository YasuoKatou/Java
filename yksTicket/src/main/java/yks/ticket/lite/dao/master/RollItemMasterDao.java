package yks.ticket.lite.dao.master;

import java.util.List;

import yks.ticket.lite.entity.master.RollItemMasterEntity;

/**
 * ロール項目マスタDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface RollItemMasterDao {
	/**
	 * レコードを登録する.
	 * @param entity ロールマスタエンティティ.
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(RollItemMasterEntity entity);


	/**
	 * ロール項目マスタを全取得する.
	 * 
	 * @return ロール項目マスタエンティティ一覧
	 * @since 0.0.1
	 */
	List<RollItemMasterEntity> findAll();
}