package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketMemoEntity;

/**
 * チケットメモテーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketMemoDao {
	/**
	 * 最大のチケットメモIDを取得する.
	 *
	 * @param ticket_id チケットID
	 * @return チケットメモID
	 * @since 0.0.1
	 */
	Long findMaxId(@Param("ticket_id") Long ticket_id);

	/**
	 * チケットメモを登録する.
	 *
	 * @param entity チケットメモエンティティ.
	 * @return 1（登録件数）
	 * @since 0.0.1
	 */
	int append(TicketMemoEntity entity);

	/**
	 * チケットメモの一覧を取得
	 * @param ticket_id チケットID
	 * @return  チケットメモエンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketMemoEntity> findByTicketId(@Param("ticket_id") Long ticket_id);
}