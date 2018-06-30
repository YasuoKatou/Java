package yksolution.demo.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import yksolution.demo.entity.UserMasterEntity;

@Mapper
public interface UserMasterDao {
  /**
   * ユーザ情報を取得する.
   * @param userId ユーザID
   * @return
   */
  UserMasterEntity findUser(@Param("userId") String userId);
}