package yks.ticket.lite.dao.master;

import java.util.List;

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
import yks.ticket.lite.entity.master.ProjectMasterEntity;
import yks.ticket.lite.entity.master.UserMasterEntity;

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
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
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

	/**
	 * プロジェクトの一覧を取得（全て）
	 */
	@Test
	@DatabaseSetup("classpath:ProjectMasterDaoTest_D01/")
	public void test_findProjects_01() {
		try {
			List<ProjectMasterEntity> list = projectMasterDao.findProjects(null);
			assertEquals("レコード数", 2, list.size());
			// #1
			ProjectMasterEntity e = list.get(0);
			assertEquals("プロジェクト識別子", e.getId(), Long.valueOf(11L));
			assertEquals("プロジェクト名称", e.getName(), "テストプロジェクト");
			assertEquals("プロジェクトの説明", e.getDescription(), "単体テストデータ#1");
			assertEquals("プロジェクト管理者", e.getManager_id(), Long.valueOf(101L));
			assertEquals("プロジェクト完了", e.getTerminated(), "no");
			assertEquals("オープンプロジェクト", e.getOpened(), "yes");
			UserMasterEntity um = e.getManager();
			assertNotNull("管理者情報", um);
			assertEquals("管理者名（性）", um.getName1(), "Ａｂｂｏｔ");
			assertEquals("管理者名（名）", um.getName2(), "Ａａｒｏｎ");
			// #2
			e = list.get(1);
			assertEquals("プロジェクト識別子", e.getId(), Long.valueOf(12L));
			assertEquals("プロジェクト名称", e.getName(), "プロジェクト Ｘ");
			assertEquals("プロジェクトの説明", e.getDescription(), "単体テストデータ#2");
			assertEquals("プロジェクト管理者", e.getManager_id(), Long.valueOf(102L));
			assertEquals("プロジェクト完了", e.getTerminated(), "yes");
			assertEquals("オープンプロジェクト", e.getOpened(), "no");
			um = e.getManager();
			assertNotNull("管理者情報", um);
			assertEquals("管理者名（性）", um.getName1(), "Ａｂｉｔｂｏｌ");
			assertEquals("管理者名（名）", um.getName2(), "Ａｃｈｉｌｌｅ－Ｃｌａｕｄｏ");
		} catch (Exception ex) {
			fail("findProjects error¥n" + ex.toString());
		}
	}

	/**
	 * プロジェクトの一覧を取得（未完了のもの）
	 */
	@Test
	@DatabaseSetup("classpath:ProjectMasterDaoTest_D01/")
	public void test_findProjects_02() {
		try {
			List<ProjectMasterEntity> list = projectMasterDao.findProjects("no");
			assertEquals("レコード数", 1, list.size());
			// #1
			ProjectMasterEntity e = list.get(0);
			assertEquals("プロジェクト識別子", e.getId(), Long.valueOf(11L));
			assertEquals("プロジェクト名称", e.getName(), "テストプロジェクト");
			// 以下省略（test_Insert_03で確認済み）
		} catch (Exception ex) {
			fail("findProjects error¥n" + ex.toString());
		}
	}
}