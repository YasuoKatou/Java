## ImportSearcher
javaのパッケージ検索で使用するデータの登録を行う。<br/>
本来、単体で使用する想定だったが、デモアプリに組込んだ方が使用用途が広がると思い方針を変更した。よって、本プロジェクトでは（基本的には）mavenで使用するjarファイルの情報をデモ用DBに登録するまでとした。
### DBにテーブルを作成
```
-- スキーマの作成
ij> create schema java;
-- jarファイルの情報を登録するテーブル
ij> create table java.jar_list (
  jar_id int not null,
  jar_file_name varchar(128) not null,
  repo_path varchar(256) not null,
  primary key (jar_id)
);
-- クラス及びインターフェースの情報と登録するテーブル
ij> create table java.class_list (
  jar_id int not null,
  class_name varchar(256) not null,
  package_name varchar(256) not null
);
-- インデックスの作成
ij> create index java.class_name_idx on java.class_list (class_name);
ij> create index java.jar_list_idx on java.jar_list (jar_file_name, repo_path);
```
### 環境変数
「derby.jar」を使用するため、ビルドパスに「derby.jar」を追加する。
### 起動方法
Eclpseより、下記の起動時引数を設定する<br/>
- 第一引数：（Derbyの）DBパス
- 第二引数：mavenのsetting.xmlのパス
何故かここでは、Eclipseを使用！？
### 注意事項
- 動作検証環境はLinuxにて実施（Windowsでは、動作しないと思う）
- setting.xmlにlocalRepositoryタグが見つからない場合、HOME/.m2/repository配下を検索する
- setting.xmlの構文チェックは行わない（当然かも）
- ローカルリポジトリの変更が発生した場合、再取込みが必要
### ワンポイント「javaのjarを登録」
起動時の第二引数のsettings.xmlを編集すれば、Javaのjarも取込める。<br/>
```
  <localRepository>/usr/lib/jvm/oracle-java8-jdk-i386/jre</localRepository>
```
### デモアプリでの実行結果
- URIの最後にクラス名またはインターフェース名を指定する
```
$ curl GET http://localhost:8080/docs/java/package/ArrayList
curl: (6) Could not resolve host: GET
[import java.util.ArrayList]
	lib (rt.jar)
[import java.util.Arrays.ArrayList]
	lib (rt.jar)
```
```
$ curl GET http://localhost:8080/docs/java/package/RequestMapping
curl: (6) Could not resolve host: GET
[import org.springframework.web.bind.annotation.RequestMapping]
	org/springframework/spring-web/5.0.8.RELEASE (spring-web-5.0.8.RELEASE.jar)
	org/springframework/spring-web/5.0.6.RELEASE (spring-web-5.0.6.RELEASE.jar)
```
- 大括弧内をJavaのソースへコピペ
- ローカルリポジトリの内容により、表示される内容も異なる