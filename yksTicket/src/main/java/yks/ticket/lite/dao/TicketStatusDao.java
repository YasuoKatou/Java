package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketStatusEntity;

/**
 * チケットステータステーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketStatusDao {
	/**
	 * プロジェクトに紐づくチケットステータスを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return チケットステータスエンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketStatusEntity> findByProject(@Param("project_id") Long project_id);
}