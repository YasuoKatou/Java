package yks.ticket.lite.dao.master;

import static org.junit.Assert.fail;

import java.util.List;

import static org.junit.Assert.assertEquals;

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

/**
 * 言語マスタDaoテストクラス.
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
public class LanguageMasterDaoTest {
	/** 言語マスタDao. */
	@Autowired private LanguageMasterDao languageMasterDao;

	/**
	 * 言語マスタ登録（IDの自動採番）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_01() {
		try {
			LanguageMasterEntity entity = LanguageMasterEntity.builder()
					.name("日本語")
					.country("japan")
					.build();
			entity.setCreateUserId(1L);
			int count = this.languageMasterDao.insert(entity);
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
			LanguageMasterEntity entity = LanguageMasterEntity.builder()
					.id(Long.MAX_VALUE)
					.name("English")
					.country("America")
					.remarks("for test")
					.build();
			entity.setCreateUserId(1L);
			int count = this.languageMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}

	/**
	 * 一覧取得（初期登録の内容を確認）
	 */
	@Test
	public void test_find_all() {
		List<LanguageMasterEntity> list = this.languageMasterDao.findAll();
		assertEquals("一覧の数" , 1, list.size());
		LanguageMasterEntity entity = list.get(0);
		assertEquals("名称", "日本語", entity.getName());
		assertEquals("国", "japan", entity.getCountry());
	}
}