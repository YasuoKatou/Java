# Java

## １．Spring Boot サンプル

### できる事
1. フィルターを複数指定可能（順番も指定可能）
1. application.ymlに定義するパスワードなどの暗号化
1. JUnit（トランザクション内でDaoのテストができる。DBデータはCSV形式が可能）
1. ログの出力(2018-07-21)。アプリケーションとフレームワークでファイル出力を行う(2018-07-22)。
1. コントローラ（リクエスト／レスポンス）のテストが行える(2018-07-29)。~~但し、コントローラのみテスト(起動方法(3))は正常終了するが、他のテストと同時にすると500(Internal server error)になる~~(2018-07-30解消)
1. DBにDerbyが使用できるようになった(2018-08-11)。
1. JUnit（リクエスト／レスポンス）(2018-08-26)
### アプリとしてできる事
1. javaのimport文で使用するパッケージ名を検索する(2018-08-17)。詳細は、ImportSearcherプロジェクト参照

### 今後の修正（目標）
1. 簡単なアプリを作成

### 起動方法
DBにDerbyを使用する場合、「-Dspring.profiles.active=derby」を追加する(2018-08-11)<br/>
(1) アプリケーションの起動
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD spring-boot:run</pre>
「$PASSWORD」は、暗号化時のパスワードを指定します。<br/><br/>
(2) JUnitの起動
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD test</pre>
「$PASSWORD」は、暗号化時のパスワードを指定します。<br/><br/>
(3) JUnitの起動(単体)
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD test -Dtest=HelloWorldTest</pre><br/><br/>

### Derbyの初期化<br/>
- DEMO本体は、spring_boot_demoプロジェクトのtables.mdを参照
- パッケージ検索アプリで使用するテーブル等は、ImportSearcherプロジェクトのREADME.mdを参照
