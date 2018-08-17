## テーブルの作成
- デモアプリ用
```
-- スキーマ
create schema demo;
-- デモアプリで使用するテーブル（2018-08-17時点）
create table demo.demo_user (
 user_id varchar(64)
,passwd varchar(64)
,authority varchar(64)
);
```
- javaパッケージ検索用
ImportSearcherプロジェクトを参照