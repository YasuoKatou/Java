# Java

## １．Spring Boot サンプル

### できる事
1. フィルターを複数指定可能（順番も指定可能）
1. application.ymlに定義するパスワードなどの暗号化
1. JUnit（トランザクション内でDaoのテストができる。DBデータはCSV形式が可能）
1. ログの出力(2018-07-21)。アプリケーションとフレームワークでファイル出力を行う(2018-07-22)。
1. コントローラ（リクエスト／レスポンス）のテストが行える(2018-07-29)。但し、コントローラのみテスト(起動方法(3))は正常終了するが、他のテストと同時にすると500(Internal server error)になる

### 今後の修正（目標）
1. JUnit（リクエスト／レスポンス）

### 起動方法
(1) アプリケーションの起動
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD spring-boot:run</pre>
「$PASSWORD」は、暗号化時のパスワードを指定します。

(2) JUnitの起動
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD test</pre>
「$PASSWORD」は、暗号化時のパスワードを指定します。

(3) JUnitの起動(単体)
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD test -Dtest=HelloWorldTest</pre>
