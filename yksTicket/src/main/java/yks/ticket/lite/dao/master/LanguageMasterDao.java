package yks.ticket.lite.dao.master;

import yks.ticket.lite.entity.master.LanguageMasterEntity;

/**
 * 言語マスタDao
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface LanguageMasterDao {
	/**
	 * レコードを登録する.
	 * 
	 * @param entity 言語マスタエンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(LanguageMasterEntity entity);
}