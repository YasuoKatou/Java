package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketKindEntity;

/**
 * チケット種類テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketKindDao {
	/**
	 * プロジェクトに紐づくチケット種類を取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return チケット進捗エンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketKindEntity> findByProject(@Param("project_id") Long project_id);
}