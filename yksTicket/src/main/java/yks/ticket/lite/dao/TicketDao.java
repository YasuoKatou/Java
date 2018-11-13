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

	/**
	 * チケットIDでチケットを取得する.
	 *
	 * @param id 取得するチケットID
	 * @return チケットエンティティ
	 * @since 0.0.1
	 */
	TicketEntity findById(@Param("id") Long id);

	/**
	 * チケットIDでチケットを取得する（関連マスタの名称付き）.
	 *
	 * @param id 取得するチケットID
	 * @return チケットエンティティ
	 * @since 0.0.1
	 */
	TicketEntity findByIdWithMaster(@Param("id") Long id);

	/**
	 * 最大のチケットIDを取得する.
	 *
	 * @return チケットID
	 * @since 0.0.1
	 */
	Long findMaxId();

	/**
	 * チケットを登録する.
	 *
	 * @param entity チケットエンティティ
	 * @return 1（登録件数）
	 * @since 0.0.1
	 */
	int append(TicketEntity entity);

	/**
	 * チケットを更新する.
	 *
	 * @param entity チケットエンティティ
	 * @return 1（更新件数）
	 * @since 0.0.1
	 */
	int update(TicketEntity entity);
}