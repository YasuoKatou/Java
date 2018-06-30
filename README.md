# Java

## １．Spring Boot サンプル

### できる事
1. フィルターを複数指定可能（順番も指定可能）
1. application.ymlに定義するパスワードなどの暗号化

### 今後の修正（目標）
1. JUnit

### 起動方法
<pre>$ ./mvnw -Dspring.main.banner-mode=off -Djasypt.encryptor.password=$PASSWORD spring-boot:run</pre>
「$PASSWORD」は、暗号化時のパスワードを指定します。