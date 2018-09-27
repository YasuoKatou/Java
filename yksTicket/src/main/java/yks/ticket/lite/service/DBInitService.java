package yks.ticket.lite.service;

import static org.junit.Assert.assertEquals;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.DBInitDao;
import yks.ticket.lite.dao.master.LanguageMasterDao;
import yks.ticket.lite.dao.master.RollGroupMasterDao;
import yks.ticket.lite.dao.master.RollItemMasterDao;
import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.entity.master.LanguageMasterEntity;
import yks.ticket.lite.entity.master.RollGroupMasterEntity;
import yks.ticket.lite.entity.master.RollItemMasterEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * ＤＢの初期化を行う.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class DBInitService {
	/** ログ出力 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	/** ＤＢ初期化Dao. */
	@Autowired private DBInitDao dbInitDao;
	/** 言語マスタDao. */
	@Autowired private LanguageMasterDao languageMasterDao;
	/** ユーザマスタDao. */
	@Autowired private UserMasterDao userMasterDao;
	/** ロールグループマスタDao. */
	@Autowired private RollGroupMasterDao rollGroupMasterDao;
	/** ロール項目マスタDao. */
	@Autowired private RollItemMasterDao rollItemMasterDao;

	/**
	 * ＤＢの初期化を行う
	 * @since 0.0.1
	 */
	public void doInit() {
		// TODO ロックテーブルが存在する場合、例外を投げる
		// テーブルの削除
		this.clearTables();
		// テーブルの生成
		this.createTables();
		// 初期データの登録
		this.insertInitDatas();
		// TODO ロックテーブルを作成
	}

	/**
	 * 初期データを登録する
	 * @since 0.0.1
	 */
	private void insertInitDatas() {
		// ユーザマスタの初期データ登録
		this.userMaster();
		// 言語マスタの初期データ登録
		this.languageMaster();
		// ロールグループマスタの初期データ登録
		this.rollGroupMaster();
		// ロール項目マスタの初期データ登録
		this.rollItemMaster();
	}

	/**
	 * 言語マスタの初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void languageMaster() {
		LanguageMasterEntity entity = LanguageMasterEntity.builder()
				.name("日本語")
				.country("japan")
				.build();
		entity.setCreateUserId(1L);
		int count = languageMasterDao.insert(entity);
		logger.info("言語マスタ登録件数 : " + count);
	}

	/**
	 * ユーザマスタの初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void userMaster() {
		UserMasterEntity entity = UserMasterEntity.builder()
				.login_id("admin")
				.passwd("admin")	// TODO 暗号化
				.name1("Katou").name2("Yasuo")
				.email("yasuokatou@gmail.com")
				.language_id(1L)
				.build();
		entity.setCreateUserId(1L);
		int count = userMasterDao.insert(entity);
		logger.info("ユーザマスタ登録件数 : " + count);
	}

	/**
	 * ロールグループマスタの初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void rollGroupMaster() {
		RollGroupMasterEntity entity = RollGroupMasterEntity.builder()
				.id(Long.valueOf(1000L))
				.name("プロジェクト")
				.build();
		entity.setCreateUserId(1L);
		int count = rollGroupMasterDao.insert(entity);

		entity.setId(Long.valueOf(2000L));
		entity.setName("ファイル");
		count += rollGroupMasterDao.insert(entity);

		entity.setId(Long.valueOf(3000L));
		entity.setName("チケット");
		count += rollGroupMasterDao.insert(entity);

		entity.setId(Long.valueOf(4000L));
		entity.setName("Wiki");
		count += rollGroupMasterDao.insert(entity);

		logger.info("ロールグループマスタ登録件数 : " + count);
	}

	/**
	 * ロール項目マスタの初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void rollItemMaster() {
		RollItemMasterEntity entity = RollItemMasterEntity.builder()
				.id(Long.valueOf(1001L))
				.name("プロジェクトの編集")
				.group_id(Long.valueOf(1000L))
				.build();
		entity.setCreateUserId(1L);
		int count = rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(1002L));
		entity.setName("プロジェクトの終了/再開");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(1003L));
		entity.setName("メンバーの管理");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(1004L));
		entity.setName("サブプロジェクトの追加（将来機能）");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(2001L));
		entity.setName("ファイルの閲覧");
		entity.setGroup_id(Long.valueOf(2000L));
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(3001L));
		entity.setName("チケットのカテゴリの管理");
		entity.setGroup_id(Long.valueOf(3000L));
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(3002L));
		entity.setName("チケットの閲覧");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(3003L));
		entity.setName("チケットの追加");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(3004L));
		entity.setName("チケットの編集");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(3005L));
		entity.setName("チケットの削除");
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(4001L));
		entity.setName("Wikiの閲覧");
		entity.setGroup_id(Long.valueOf(4000L));
		count += rollItemMasterDao.insert(entity);

		entity.setId(Long.valueOf(4002L));
		entity.setName("Wikiページの編集");
		count += rollItemMasterDao.insert(entity);

		logger.info("ロール項目マスタ登録件数 : " + count);
	}

	/**
	 * テーブルの生成
	 * @since 0.0.1
	 */
	private void createTables() {
		logger.trace("DBInitService.doInit() start");
		// プロジェクトマスタ
		dbInitDao.createProjectMaster();
		logger.info("created Project master table");
		// ユーザマスタ
		dbInitDao.createUserMaster();
		logger.info("created User master table");
		// 言語マスタ
		dbInitDao.createLanguageMaster();
		logger.info("created Language master table");
		// ロールグループマスタ
		dbInitDao.createRollGroupMaster();
		logger.info("created Roll Group master table");
		// ロール項目マスタ
		dbInitDao.createRollItemMaster();
		logger.info("created Roll master table");
		// セッション管理
		dbInitDao.createSessionTran();
		logger.info("created sesion table");
	}

	/**
	 * テーブルの削除
	 * @since 0.0.1
	 */
	private void clearTables() {
		// プロジェクトマスタ
		try {
			dbInitDao.dropProjectMaster();
			logger.info("dropped Project master table");
		} catch (Exception ex) {
			logger.debug("Project master table can't drop cause by " + ex.getMessage());
		}
		// ユーザマスタ
		try {
			dbInitDao.dropUserMaster();
			logger.info("dropped User master table");
		} catch (Exception ex) {
			logger.debug("User master table can't drop cause by " + ex.getMessage());
		}
		// 言語マスタ
		try {
			dbInitDao.dropLanguageMaster();
			logger.info("dropped Language master table");
		} catch (Exception ex) {
			logger.debug("Language master table can't drop cause by " + ex.getMessage());
		}
		// ロールグループマスタ
		try {
			dbInitDao.dropRollGroupMaster();
			logger.info("dropped Roll Group master table");
		} catch (Exception ex) {
			logger.debug("Roll Group master table can't drop cause by " + ex.getMessage());
		}
		// ロール項目マスタ
		try {
			dbInitDao.dropRollItemMaster();
			logger.info("dropped Roll master table");
		} catch (Exception ex) {
			logger.debug("Roll master table can't drop cause by " + ex.getMessage());
		}
		// セッション管理テーブル
		try {
			dbInitDao.dropSessionTran();
			logger.info("dropped session table");
		} catch (Exception ex) {
			logger.debug("session table can't drop cause by " + ex.getMessage());
		}
	}
}