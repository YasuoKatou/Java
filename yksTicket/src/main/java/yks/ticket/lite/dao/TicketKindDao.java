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
	 * @return チケット種類エンティティ一覧
	 * @since 0.0.1
	 */
	List<TicketKindEntity> findByProject(@Param("project_id") Long project_id);

	/**
	 * プロジェクトの全種類を無効に設定する.
	 *
	 * @param project_id プロジェクトID
	 * @return 更新件数
	 * @since 0.0.1
	 */
	int setUnavailable(@Param("project_id") Long project_id);

	/**
	 * チケット種類を登録する.
	 *
	 * @param entity チケット種類エンティティ
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int appendItem(TicketKindEntity entity);

	/**
	 * チケット種類を更新する.
	 *
	 * @param entity チケット種類エンティティ
	 * @return 1 (更新レコード数)
	 * @since 0.0.1
	 */
	int updateItem(TicketKindEntity entity);

	/**
	 * チケット種類の最大のIDを取得する.
	 *
	 * @param project_id プロジェクトID
	 * @return 最大の種類ID
	 * @since 0.0.1
	 */
	Long getMaxId(@Param("project_id") Long project_id);
}