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

	/**
	 * プロジェクトの全進捗を無効に設定する.
	 *
	 * @param project_id プロジェクトID
	 * @return 更新件数
	 * @since 0.0.1
	 */
	int setUnavailable(@Param("project_id") Long project_id);

	/**
	 * チケット進捗を登録する.
	 *
	 * @param entity チケット進捗エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int appendItem(TicketProgressEntity entity);

	/**
	 * チケット進捗を更新する.
	 *
	 * @param entity チケット進捗エンティティ
	 * @return 1 (更新レコード数)
	 * @since 0.0.1
	 */
	int updateItem(TicketProgressEntity entity);

	/**
	 * チケット進捗の最大のIDを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return 最大の進捗ID
	 * @since 0.0.1
	 */
	Long getMaxId(@Param("project_id") Long project_id);
}