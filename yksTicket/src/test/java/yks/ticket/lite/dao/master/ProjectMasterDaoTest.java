package yks.ticket.lite.dao.master;

import static org.junit.Assert.fail;
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

import yks.ticket.lite.entity.master.ProjectMasterEntity;

/**
 * プロジェクトマスタDaoテストクラス.
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
//@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
public class ProjectMasterDaoTest {
	/** プロジェクトマスタDao. */
	@Autowired private ProjectMasterDao projectMasterDao;

	/**
	 * プロジェクトマスタ登録（IDの自動採番）
	 * @since 0.0.1
	 */
	@Test
	public void test_Insert_01() {
		try {
			ProjectMasterEntity entity = ProjectMasterEntity.builder()
					.name("テストプロジェクト")
					.manager_id(1L)
					.opened("yes")
					.build();
			entity.setCreateUserId(1L);
			int count = projectMasterDao.insert(entity);
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
			ProjectMasterEntity entity = ProjectMasterEntity.builder()
					.id(Long.MAX_VALUE)
					.name("テストプロジェクト")
					.description("プロジェクトの説明")
					.manager_id(1L)
					.opened("yes")
					.build();
			entity.setCreateUserId(1L);
			int count = projectMasterDao.insert(entity);
			assertEquals("登録数", 1, count);
		} catch (Exception ex) {
			fail("insert error¥n" + ex.toString());
		}
	}
}