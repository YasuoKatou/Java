package yks.ticket.lite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import yks.ticket.lite.controller.master.LoginControllerTest;
import yks.ticket.lite.controller.master.RollMasterControllerTest;
import yks.ticket.lite.dao.SessionDaoTest;

@RunWith(Suite.class)
@SuiteClasses({
	  SessionDaoTest.class
	, LoginControllerTest.class				// ログインコントローラのテスト
	, RollMasterControllerTest.class		// ロールマスタコントローラのテスト
})
public class TranGroupTest {}