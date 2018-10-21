package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.TicketEntity;

/**
 * チケットテーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface TicketDao {
	/**
	 * プロジェクトに紐づくチケットを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return チケットエンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketEntity> findByProject(@Param("project_id") Long project_id);
}