package yks.ticket.lite.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;
import yks.ticket.lite.entity.SessionEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * セッション管理Daoテストクラス.
 * 
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT, value = "server.port=8080")
@Transactional
@TestExecutionListeners({
  DependencyInjectionTestExecutionListener.class,
  DirtiesContextTestExecutionListener.class,
  TransactionDbUnitTestExecutionListener.class,
  DbUnitTestExecutionListener.class})
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
public class SessionDaoTest {
	/** セッション管理テーブルDao. */
	@Autowired private SessionDao sessionDao;

	/**
	 * セッション管理テーブルに追加する.
	 * @since 0.0.1
	 */
	@Test
	public void test_insert() {
		try {
			int count = sessionDao.insert(SessionEntity.builder()
					.session_id("123")
					.user_id(Long.valueOf(1L))
					.build());
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * セッションIDからユーザ情報を取する.
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:SessionDaoTest_D01/")
	public void test_findUserBySessionId() {
		try {
			UserMasterEntity entity = sessionDao.findUserBySessionId("SESSIONID-002");
			assertNotNull("取得データあり", entity);
			assertEquals("ID", entity.getId(), Long.valueOf(101L));
			assertEquals("性", entity.getName1(), "Ａｂｂｏｔ");
			assertEquals("名", entity.getName2(), "Ａａｒｏｎ");
			assertEquals("メールアドレス", entity.getEmail(), "hoge@example.com");
			assertEquals("言語ID", entity.getLanguage_id(), Long.valueOf(201L));
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}
}