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

	/**
	 * プロジェクトの全優先順位を無効に設定する.
	 *
	 * @param project_id プロジェクトID
	 * @return 更新件数
	 * @since 0.0.1
	 */
	int setUnavailable(@Param("project_id") Long project_id);

	/**
	 * チケット優先順位を登録する.
	 *
	 * @param entity チケット優先順位エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int appendItem(TicketPriorityEntity entity);

	/**
	 * チケット優先順位を更新する.
	 *
	 * @param entity チケット優先順位エンティティ
	 * @return 1 (更新レコード数)
	 * @since 0.0.1
	 */
	int updateItem(TicketPriorityEntity entity);

	/**
	 * チケット優先順位の最大のIDを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return 最大の優先順位ID
	 * @since 0.0.1
	 */
	Long getMaxId(@Param("project_id") Long project_id);
}