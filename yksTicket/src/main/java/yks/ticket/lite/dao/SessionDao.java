package yks.ticket.lite.dao;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.SessionEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

public interface SessionDao {
	/**
	 * セッションを登録する
	 * @param entity セッション管理エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert (SessionEntity entity);

	/**
	 * セッションIDからユーザ情報を取得する.
	 * @param session_id セッションID
	 * @return ユーザマスタエンティティ.
	 * @since 0.0.1
	 */
	UserMasterEntity findUserBySessionId(@Param("session_id") String session_id);
}