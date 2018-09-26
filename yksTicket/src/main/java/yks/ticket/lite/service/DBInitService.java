package yks.ticket.lite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.DBInitDao;
import yks.ticket.lite.dao.master.LanguageMasterDao;
import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.entity.master.LanguageMasterEntity;
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
		// ロールマスタ
		dbInitDao.createRollMaster();
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
		// ロールマスタ
		try {
			dbInitDao.dropRollMaster();
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