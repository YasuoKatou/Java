package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.RollSettingEntity;

/**
 * ロール設定テーブルDao.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface RollSettingDao {
	/**
	 * ロールを登録する.
	 *
	 * @param entity ロール設定エンティティ.
	 * @return 1 (登録レコード数)
	 * @since 0.0.1
	 */
	int insert(RollSettingEntity entity);

	/**
	 * ロール設定情報を取得する.
	 *
	 * @param rollNameId ロール名称ID
	 * @return ロール設定エンティティ一覧
	 * @since 0.0.1
	 */
	List<RollSettingEntity> findSetting(@Param("rollNameId") Long rollNameId);

	/**
	 * ロール名称IDでレコードを削除する.
	 *
	 * @param rollNameId ロール名称ID
	 * @return 削除件数
	 * @since 0.0. 1
	 */
	int deleteByRollNameId(@Param("rollNameId") Long rollNameId);
}