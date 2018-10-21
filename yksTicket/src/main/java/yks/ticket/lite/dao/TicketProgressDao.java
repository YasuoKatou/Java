package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketProgressEntity;

/**
 * チケット進捗テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketProgressDao {
	/**
	 * プロジェクトに紐づくチケット進捗を取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return チケット進捗エンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketProgressEntity> findByProject(@Param("project_id") Long project_id);
}