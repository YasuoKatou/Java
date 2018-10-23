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

	/**
	 * プロジェクトの全ステータスを無効に設定する.
	 *
	 * @param project_id プロジェクトID
	 * @return 更新件数
	 * @since 0.0.1
	 */
	int setUnavailable(@Param("project_id") Long project_id);

	/**
	 * チケットステータスを登録する.
	 *
	 * @param entity チケットステータスエンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int appendItem(TicketStatusEntity entity);

	/**
	 * チケットステータスを更新する.
	 *
	 * @param entity チケットステータスエンティティ
	 * @return 1 (更新レコード数)
	 * @since 0.0.1
	 */
	int updateItem(TicketStatusEntity entity);

	/**
	 * チケットステータスの最大のIDを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return 最大のステータスID
	 * @since 0.0.1
	 */
	Long getMaxId(@Param("project_id") Long project_id);
}