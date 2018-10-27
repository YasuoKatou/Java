package yks.ticket.lite.service;

import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import yks.ticket.lite.common.TicketApConstatnt.FilePath;
import yks.ticket.lite.dao.DBInitDao;
import yks.ticket.lite.dto.ApInitData;
import yks.ticket.lite.dto.ApInitData.ApInitTable;

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

	@Autowired private ResourceLoader resourceLoader;
	@Autowired private ApplicationContext applicationContext;

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
		ObjectMapper mapper = new ObjectMapper();
		Resource resource = resourceLoader.getResource("classpath:" + FilePath.AP_INIT_JSON);
		try {
			// JSON読込み ＆ 初期データ投入情報Dtoへのマッピング
			ApInitData apInitData = mapper.readValue(resource.getFile(), ApInitData.class);
			// テーブルごとに初期データの投入を実施
			for (ApInitTable table : apInitData.getInitDataList()) {
				System.out.println(table.getTitle());
				// Daoをbeanから取得
				Object dao = applicationContext.getBean(table.getDao());
				// エンティティを取得
				Class entityClass = Class.forName(table.getEntity());
				// 初期データを登録
				int count = 0;
				for (Map<String, Object> rec : table.getValues()) {
					String json = mapper.writeValueAsString(rec);
					Object entity = mapper.readValue(json, entityClass);
					System.out.println("entity : " + entity.toString());
					Method method = dao.getClass().getMethod(table.getMethod(), entityClass);
					System.out.println("method : " + method.toString());
					count += (int)method.invoke(dao, entity);
				}
				System.out.println("登録件数 : " + count);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Not yet implemented : " + ex.toString());
		}
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