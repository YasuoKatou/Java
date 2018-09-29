package yks.ticket.lite.dao;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.SessionEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * セッション管理テーブルDao.
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface SessionDao {
	/**
	 * セッションを登録する
	 * 
	 * @param entity セッション管理エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert (SessionEntity entity);

	/**
	 * セッションIDからユーザ情報を取得する.
	 * 
	 * @param session_id セッションID
	 * @return ユーザマスタエンティティ.
	 * @since 0.0.1
	 */
	UserMasterEntity findUserBySessionId(@Param("session_id") String session_id);

	/**
	 * セッションIDでレコードを削除する.
	 * 
	 * @param session_id セッションID
	 * @return 1 (削除レコード数)
	 * @since 0.0.1
	 */
	int deleteBySessionId(@Param("session_id") String session_id);

	/**
	 * ユーザ識別子でレコードを削除する.
	 * 
	 * @param user_id ユーザ識別子
	 * @return 削除したレコード数
	 * @since 0.0.1
	 */
	int deleteByUserId(@Param("user_id") Long user_id);
}