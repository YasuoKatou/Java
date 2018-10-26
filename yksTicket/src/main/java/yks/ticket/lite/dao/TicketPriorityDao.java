package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketPriorityEntity;

/**
 * チケット優先順位テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketPriorityDao {
	/**
	 * プロジェクトに紐づくチケット優先順位を取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return チケット進捗エンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketPriorityEntity> findByProject(@Param("project_id") Long project_id);
}