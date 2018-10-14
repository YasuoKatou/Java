package yks.ticket.lite.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
import yks.ticket.lite.entity.ProjectStatusEntity;

/**
 * プロジェクトステータス管理Daoテストクラス.
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
public class ProjectStatusDaoTest {
	/** プロジェクトステータス管理テーブルDao. */
	@Autowired private ProjectStatusDao projectStatusDao;

	/**
	 * プロジェクトステータス一覧取得を確認
	 * @since 0.0.1
	 */
	@Test
	@DatabaseSetup("classpath:ProjectStatusDaoTest_D01/")
	public void test_findByProject() {
		Long projectId = Long.valueOf(1L);
		List<ProjectStatusEntity> list = this.projectStatusDao.findByProject(projectId);
		assertNotNull("取得データあり", list);
		assertEquals("取得件数", list.size(), 3);
		// +0
		ProjectStatusEntity entity = list.get(0);
		assertEquals("+0.ステータスID", entity.getId(), Long.valueOf(1L));
		assertEquals("+0.ステータス名称", entity.getName(), "未着手");
		assertEquals("+0.表示順", entity.getDispSeq(), 10);
		assertEquals("+0.バージョン", entity.getVersionNo(), Integer.valueOf(100));
		// +1
		entity = list.get(1);
		assertEquals("+1.ステータスID", entity.getId(), Long.valueOf(2L));
		assertEquals("+1.ステータス名称", entity.getName(), "10%");
		assertEquals("+1.表示順", entity.getDispSeq(), 20);
		assertEquals("+1.バージョン", entity.getVersionNo(), Integer.valueOf(200));
		// +2
		entity = list.get(2);
		assertEquals("+2.ステータスID", entity.getId(), Long.valueOf(3L));
		assertEquals("+2.ステータス名称", entity.getName(), "完了");
		assertEquals("+2.表示順", entity.getDispSeq(), 30);
		assertEquals("+2.バージョン", entity.getVersionNo(), Integer.valueOf(300));
	}
}