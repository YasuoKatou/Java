package yksolution.demo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class JavaPackageEntity {
  /** パッケージ名 */
  private String packageName;
  /** リポジトリのパス */
  private String repositoryPath;
  /** jarファイル名 */
  private String jarFileName;
}