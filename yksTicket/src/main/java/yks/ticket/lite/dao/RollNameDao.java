package yks.ticket.lite.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yks.ticket.lite.entity.RollNameEntity;

/**
 * ロール名称テーブルDao.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
public interface RollNameDao {
	/**
	 * ロール名称を登録する.
	 * 
	 * @param entity ロール名称エンティティ.
	 * @return 1 (登録レコード数)
	 */
	int insert(RollNameEntity entity);

	/**
	 * ロールの一覧を取得する.
	 *
	 * @return ロール設定エンティティ一覧.
	 * @since 0.0.1
	 */
	List<RollNameEntity> findAll();

	/**
	 * PK指定でロール名称を取得する.
	 *
	 * @param id ロール名称ID
	 * @return ロール設定エンティティ.
	 * @since 0.0.1
	 */
	RollNameEntity findByPk(@Param("id") Long id);
}