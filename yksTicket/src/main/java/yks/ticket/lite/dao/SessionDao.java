package yks.ticket.lite.dao;

import yks.ticket.lite.entity.SessionEntity;

public interface SessionDao {
	/**
	 * セッションを登録する
	 * @param entity セッション管理エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert (SessionEntity entity);
}