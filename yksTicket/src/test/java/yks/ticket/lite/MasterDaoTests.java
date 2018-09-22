package yks.ticket.lite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import yks.ticket.lite.dao.master.LanguageMasterDaoTest;
import yks.ticket.lite.dao.master.ProjectMasterDaoTest;
import yks.ticket.lite.dao.master.UserMasterDaoTest;

/**
 * マスタ系Dao一括テスト.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@RunWith(Suite.class)
@SuiteClasses({
	  LanguageMasterDaoTest.class		// 言語マスタ
	, UserMasterDaoTest.class 			// ユーザマスタ
	, ProjectMasterDaoTest.class		// プロジェクトマスタ
	})
public class MasterDaoTests {}