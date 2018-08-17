package yksolution.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import yksolution.demo.entity.JavaPackageEntity;

public interface JavaPackageDao {
  /**
   * classまたはinterface名でパッケージのFQCNを取得する.
   * @param className classまたはinterface名
   * @return
   */
  List<JavaPackageEntity> findByClassName(@Param("className") String className);
}