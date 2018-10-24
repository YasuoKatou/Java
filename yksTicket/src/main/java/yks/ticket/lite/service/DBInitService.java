package yks.ticket.lite.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.DBInitDao;
import yks.ticket.lite.dao.RollNameDao;
import yks.ticket.lite.dao.RollSettingDao;
import yks.ticket.lite.dao.TicketProgressDao;
import yks.ticket.lite.dao.TicketStatusDao;
import yks.ticket.lite.dao.master.LanguageMasterDao;
import yks.ticket.lite.dao.master.RollGroupMasterDao;
import yks.ticket.lite.dao.master.RollItemMasterDao;
import yks.ticket.lite.dao.master.UserMasterDao;
import yks.ticket.lite.entity.RollNameEntity;
import yks.ticket.lite.entity.RollSettingEntity;
import yks.ticket.lite.entity.TicketProgressEntity;
import yks.ticket.lite.entity.TicketStatusEntity;
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
	/** ロール名称Dao. */
	@Autowired private RollNameDao rollNameDao;
	/** ロール設定Dao. */
	@Autowired private RollSettingDao rollSettingDao;
	/** チケットステータスDao */
	@Autowired private TicketStatusDao ticketStatusDao;
	/** チケット進捗Dao */
	@Autowired private TicketProgressDao ticketProgressDao;

	/** 管理者ユーザID */
	public static final Long ADMIN_USER_ID = Long.valueOf(1L);

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
		// ロール名称／設定の初期データ登録
		this.rollNameAndSetting();
		// チケットスタータスの初期データ登録
		this.ticketStatusInitData();
		// チケット進捗の初期データ登録
		this.ticketProgressInitData();
		// TODO チケット種類の初期データ登録
		// TODO チケット優先順位の初期データ登録
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
		entity.setCreateUserId(ADMIN_USER_ID);
		int count = languageMasterDao.insert(entity);
		logger.info("言語マスタ登録件数 : " + count);
	}

	/**
	 * ユーザマスタの初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void userMaster() {
		UserMasterEntity entity = UserMasterEntity.builder()
				.id(ADMIN_USER_ID)
				.login_id("admin")
				.passwd("admin")	// TODO 暗号化
				.name1("Katou").name2("Yasuo")
				.email("yasuokatou@gmail.com")
				.language_id(1L)
				.build();
		entity.setCreateUserId(ADMIN_USER_ID);
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
		entity.setCreateUserId(ADMIN_USER_ID);
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
		entity.setCreateUserId(ADMIN_USER_ID);
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
	 * ロール名称／設定の初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void rollNameAndSetting() {
		// ロール名称
		Long rollNameId = Long.valueOf(1L);
		RollNameEntity nameEntity = RollNameEntity.builder()
				.id(rollNameId)
				.name("システム管理者")
				.description("全権限を与える")
				.build();
		nameEntity.setCreateUserId(ADMIN_USER_ID);
		int count = this.rollNameDao.insert(nameEntity);
		logger.info("管理者ロール名称登録件数 : " + count);

		// ロール設定
		RollSettingEntity rollEntity = RollSettingEntity.builder()
				.rollNameId(rollNameId)
				.rollItemId(Long.valueOf(1001L))			// プロジェクトの編集
				.build();
		rollEntity.setCreateUserId(ADMIN_USER_ID);
		count = this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(1002L));		// プロジェクトの終了/再開
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(1003L));		// メンバーの管理
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(1004L));		// サブプロジェクトの追加
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(2001L));		// ファイルの閲覧
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(3001L));		// チケットのカテゴリの管理
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(3002L));		// チケットの閲覧
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(3003L));		// チケットの追加
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(3004L));		// チケットの編集
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(3005L));		// チケットの削除
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(4001L));		// Wikiの閲覧
		count += this.rollSettingDao.insert(rollEntity);
		rollEntity.setRollItemId(Long.valueOf(4002L));		// Wikiページの編集
		count += this.rollSettingDao.insert(rollEntity);

		logger.info("管理者ロール設定登録件数 : " + count);
	}

	/**
	 * チケットステータスの初期データ登録を行う.
	 * @since 0.0.1 
	 */
	private void ticketStatusInitData() {
		TicketStatusEntity entity = TicketStatusEntity.builder()
				.project_id(Long.valueOf(0L))
				.build();
		entity.setCreateUserId(ADMIN_USER_ID);

		entity.setId(Long.valueOf(1L));
		entity.setDisp_seq(Integer.valueOf(1));
		entity.setName("未着手");
		int count = this.ticketStatusDao.appendItem(entity);

		entity.setId(Long.valueOf(2L));
		entity.setDisp_seq(Integer.valueOf(2));
		entity.setName("作業中");
		count += this.ticketStatusDao.appendItem(entity);

		entity.setId(Long.valueOf(3L));
		entity.setDisp_seq(Integer.valueOf(3));
		entity.setName("完了");
		count += this.ticketStatusDao.appendItem(entity);

		logger.info("チケットステータス登録件数 : " + count);
	}

	/**
	 * チケット進捗の初期データ登録を行う.
	 * @since 0.0.1
	 */
	private void ticketProgressInitData() {
		TicketProgressEntity entity = TicketProgressEntity.builder()
				.project_id(Long.valueOf(0L))
				.build();
		entity.setCreateUserId(ADMIN_USER_ID);

		entity.setId(Long.valueOf(1L));
		entity.setDisp_seq(Integer.valueOf(1));
		entity.setName("10%");
		int count = this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(2L));
		entity.setDisp_seq(Integer.valueOf(2));
		entity.setName("30%");
		count += this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(3L));
		entity.setDisp_seq(Integer.valueOf(3));
		entity.setName("30%");
		count += this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(4L));
		entity.setDisp_seq(Integer.valueOf(4));
		entity.setName("50%");
		count += this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(5L));
		entity.setDisp_seq(Integer.valueOf(5));
		entity.setName("70%");
		count += this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(6L));
		entity.setDisp_seq(Integer.valueOf(6));
		entity.setName("90%");
		count += this.ticketProgressDao.appendItem(entity);

		entity.setId(Long.valueOf(7L));
		entity.setDisp_seq(Integer.valueOf(7));
		entity.setName("100%");
		count += this.ticketProgressDao.appendItem(entity);

		logger.info("チケット進捗登録件数 : " + count);
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
		// ロール名称
		dbInitDao.createRollNameTran();
		logger.info("created roll name table");
		// ロール設定
		dbInitDao.createRollSettingTran();
		logger.info("created roll table");
		// チケット
		dbInitDao.createTicketTran();
		logger.info("created ticket table");
		// チケットメモ
		dbInitDao.createTicketMemoTran();
		logger.info("created ticket memo table");
		// 履歴チケット
		dbInitDao.createTicketHistory();
		logger.info("created History ticket table");
		// 履歴チケットメモ
		dbInitDao.createTicketMemoHistory();
		logger.info("created History ticket memo table");
		// プロジェクトステータス管理
		dbInitDao.createProjectStatusTran();
		logger.info("created project status table");
		// チケットステータス管理
		dbInitDao.createTicketStatusTran();
		logger.info("created ticket status table");
		// チケット進捗管理
		dbInitDao.createTicketProgressTran();
		logger.info("created ticket progress table");
		// チケット種類管理
		dbInitDao.createTicketKindTran();
		logger.info("created ticket kind table");
		// チケット優先順位管理
		dbInitDao.createTicketPriorityTran();
		logger.info("created ticket priority table");
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
		// ロール名称テーブル
		try {
			dbInitDao.dropRollNameTran();
			logger.info("dropped roll name table");
		} catch (Exception ex) {
			logger.debug("roll name table can't drop cause by " + ex.getMessage());
		}
		// ロール設定テーブル
		try {
			dbInitDao.dropRollSettingTran();
			logger.info("dropped roll table");
		} catch (Exception ex) {
			logger.debug("roll table can't drop cause by " + ex.getMessage());
		}
		// チケットテーブル
		try {
			dbInitDao.dropTicketTran();
			logger.info("dropped ticket table");
		} catch (Exception ex) {
			logger.debug("ticket table can't drop cause by " + ex.getMessage());
		}
		// チケットメモテーブル
		try {
			dbInitDao.dropTicketMemoTran();
			logger.info("dropped ticket memo table");
		} catch (Exception ex) {
			logger.debug("ticket memo table can't drop cause by " + ex.getMessage());
		}
		// 履歴チケットテーブル
		try {
			dbInitDao.dropTicketHistory();
			logger.info("dropped history ticket table");
		} catch (Exception ex) {
			logger.debug("history ticket table can't drop cause by " + ex.getMessage());
		}
		// 履歴チケットメモテーブル
		try {
			dbInitDao.dropTicketMemoHistory();
			logger.info("dropped history ticket memo table");
		} catch (Exception ex) {
			logger.debug("history ticket memo table can't drop cause by " + ex.getMessage());
		}
		// プロジェクトステータス管理テーブル
		try {
			dbInitDao.dropProjectStatusTran();
			logger.info("dropped project status table");
		} catch (Exception ex) {
			logger.debug("project status table can't drop cause by " + ex.getMessage());
		}
		// チケットステータス管理テーブル
		try {
			dbInitDao.dropTicketStatusTran();
			logger.info("dropped ticket status table");
		} catch (Exception ex) {
			logger.debug("project ticket status table can't drop cause by " + ex.getMessage());
		}
		// チケット進捗テーブル
		try {
			dbInitDao.dropTicketProgressTran();
			logger.info("dropped ticket progress table");
		} catch (Exception ex) {
			logger.debug("project ticket progress table can't drop cause by " + ex.getMessage());
		}
		// チケット種類管理テーブル
		try {
			dbInitDao.dropTicketKindTran();
			logger.info("dropped ticket kind table");
		} catch (Exception ex) {
			logger.debug("project ticket kind table can't drop cause by " + ex.getMessage());
		}
		// チケット優先順位管理テーブル
		try {
			dbInitDao.dropTicketPriorityTran();
			logger.info("dropped ticket priority table");
		} catch (Exception ex) {
			logger.debug("project ticket priority table can't drop cause by " + ex.getMessage());
		}
	}
}