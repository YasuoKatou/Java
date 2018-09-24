## ＤＢの初期化
#### Apache Derby DB の初期化
	$ ij
	ij> connect 'jdbc:derby:/mnt/hdd500/home/yasuokatou/develop/workspaces/DBs/yksTicketDB;create=true';
	ij> CREATE SCHEMA AUTHORIZATION yksTicket;
#### application.ylmの設定
1. 接続文字列の「jdbc:derby:/mnt/hdd500/home/yasuokatou/develop/workspaces/DBs/yksTicketDB」をapplication.ylmのspring.datasource.urlに記述する
1. スキーマ名「yksTicket」をapplication.ylmのspring.datasource.usernameに記述する


### その他
#### curl コマンド
1. レスポンスヘッダーを確認する  
	curl -i ...（「-i」オプションを追加する）
1. リクエストヘッダーも表示する  
	curl -v ...（「-v」オプションを追加する）
1. POSTする  
	curl -X POST -s localhost:8080//yksticket/maintenance/projects ...
1. 形式のパラメータを設定  
	curl -H 'Content-Type:application/json' -d '{"alive":null}' ...
