package yksolution.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import yksolution.demo.dao.JavaPackageDao;
import yksolution.demo.entity.JavaPackageEntity;

@Service
public class DocumentSearchService {
  @Autowired private JavaPackageDao javaPackageDao;

  @Data
  @Builder
  @ToString
  private static class PackageDto {
    /** パッケージ名 */
    private String packageName;
    /** リポジトリ情報一覧 */
    private List<RepositoryDto> repositoryDtoList;

    @Data
    @Builder
    @ToString
    private static class RepositoryDto {
      /** リポジトリのパス */
      private String repositoryPath;
      /** jarファイル名 */
      private String jarFileName;
    }
  }

  /**
   * クラス名またはインターフェース名からパッケージ情報一覧を取得する.
   * @param name 検索するクラス名またはインターフェース名
   * @return パッケージ情報一覧
   */
  public String searchPackage(String name) {
    StringBuilder content = new StringBuilder();
    List<PackageDto> packageDtoList = null;
    PackageDto packageDto = null;
    // DBよりパッケージ情報を取得
    List<JavaPackageEntity> list = javaPackageDao.findByClassName(name);
    if (CollectionUtils.isNotEmpty(list)) {
      // パッケージ名で並び替える
      list.sort(Comparator.comparing(JavaPackageEntity::getPackageName));
      // パッケージ情報を内部データ構造に編集
      for (JavaPackageEntity entity : list) {
        PackageDto.RepositoryDto repositoryDto = PackageDto.RepositoryDto.builder()
          .repositoryPath(entity.getRepositoryPath())
          .jarFileName(entity.getJarFileName())
          .build();
        if (packageDtoList == null) {
          // 初回
          packageDtoList = new ArrayList<>();
          packageDto = PackageDto.builder()
            .packageName(entity.getPackageName())
            .repositoryDtoList(new ArrayList<>())
            .build();
          packageDtoList.add(packageDto);
          packageDto.getRepositoryDtoList().add(repositoryDto);
        } else {
          // 2件目以降
          if (packageDto.getPackageName().equals(entity.getPackageName())) {
            // 同名のパッケージが連続する場合、リポジトリ情報のみを編集
            packageDto.getRepositoryDtoList().add(repositoryDto);
          } else {
              packageDto = PackageDto.builder()
                .packageName(entity.getPackageName())
                .repositoryDtoList(new ArrayList<>())
                .build();
              packageDtoList.add(packageDto);
              packageDto.getRepositoryDtoList().add(repositoryDto);
          }
        }
      }
      // 戻り値を編集
      for (PackageDto pkgDto : packageDtoList) {
        content.append("[import ").append(pkgDto.getPackageName()).append(".")
        .append(name).append("]\n");
        for (PackageDto.RepositoryDto repoDto : pkgDto.getRepositoryDtoList()) {
          content.append("\t").append(repoDto.getRepositoryPath())
            .append(" (").append(repoDto.getJarFileName()).append(")\n");
        }
      }
    } else {
      content.append("[").append(name).append("] is not registed ...\n");
    }
    return content.toString();
  }
}