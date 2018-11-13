package yks.ticket.lite.dao;

import org.apache.ibatis.annotations.Param;

/**
 * チケット履歴テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketHistoryDao {

	/**
	 * チケットをチケット履歴にコピーする.
	 * @param id 履歴にコピーするチケットID
	 * @return 1（登録件数）
	 * @since 0.0.1
	 */
	int copyToHistory(@Param("id") Long id);
}