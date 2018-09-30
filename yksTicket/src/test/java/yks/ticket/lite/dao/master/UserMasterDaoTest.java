package yks.ticket.lite.dao.master;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

import yks.ticket.lite.common.CsvDataSetLoader;
import yks.ticket.lite.entity.master.LanguageMasterEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

/**
 * ユーザマスタDaoテストクラス.
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
public class UserMasterDaoTest {
	/** ユーザマスタDao. */
	@Autowired private UserMasterDao userMasterDao;

	/**
	 * 言語マスタ登録（IDの自動採番）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_01() {
		try {
			UserMasterEntity entity = UserMasterEntity.builder()
					.login_id("admin")
					.passwd("admin")	// TODO 暗号化
					.name1("Katou").name2("Yasuo")
					.email("yasuokatou@gmail.com")
					.language_id(1L)
					.build();
			entity.setCreateUserId(1L);
			int count = userMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * 言語マスタ登録（ID指定で成功する）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_02() {
		try {
			UserMasterEntity entity = UserMasterEntity.builder()
					.id(Long.MAX_VALUE)
					.login_id("admin")
					.passwd("passwd")
					.name1("性").name2("名")
					.email("example@email.com")
					.language_id(1L)
					.build();
			entity.setCreateUserId(1L);
			int count = userMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * ログインIDでユーザマスタを取得する.
	 * @since 0.0.1
	 */
	@Test
	public void test_findByLoginId() {
		try {
			UserMasterEntity in = UserMasterEntity.builder()
					.login_id("admin")
					.build();
			UserMasterEntity entity = userMasterDao.findByLoginId(in);
			assertNotNull("該当レコードあり", entity);
			assertEquals("識別子", entity.getId(), Long.valueOf(1L));
			assertEquals("パスワード", entity.getPasswd(), "admin");		// TODO 暗号化
			assertEquals("性", entity.getName1(), "Katou");
			assertEquals("名", entity.getName2(), "Yasuo");
			assertEquals("メールアドレス", entity.getEmail(), "yasuokatou@gmail.com");
			assertEquals("使用言語ID", entity.getLanguage_id(), Long.valueOf(1L));
			LanguageMasterEntity language = entity.getLanguage();
			assertNotNull("言語情報", language);
			assertEquals("言語名", language.getName(), "日本語");
			assertEquals("言語国名", language.getCountry(), "japan");
		} catch (Exception ex) {
			fail("findByLoginId error¥n" + ex.toString());
		}
	}
}