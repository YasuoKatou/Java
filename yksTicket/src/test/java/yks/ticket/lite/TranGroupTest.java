package yks.ticket.lite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import yks.ticket.lite.controller.master.LoginControllerTest;
import yks.ticket.lite.controller.master.RollMasterControllerTest;
import yks.ticket.lite.dao.ProjectStatusDaoTest;
import yks.ticket.lite.dao.SessionDaoTest;
import yks.ticket.lite.service.ProjectStatusServiceTest;

@RunWith(Suite.class)
@SuiteClasses({
	  SessionDaoTest.class					// セッション管理Daoテストクラス.
	, LoginControllerTest.class				// ログインコントローラのテスト
	, RollMasterControllerTest.class		// ロールマスタコントローラのテスト
	, ProjectStatusDaoTest.class			// プロジェクトステータス管理Daoテストクラス.
	, ProjectStatusServiceTest.class		// プロジェクトステータ管理スサービスのテストクラス.
})
public class TranGroupTest {}